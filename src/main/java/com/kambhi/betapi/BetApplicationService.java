package com.kambhi.betapi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.kambhi.betapi.logger.Logger;

@Service
public class BetApplicationService {

	private List<Bet> stakes = new ArrayList<>(Arrays.asList(new Bet(1234, 888, 4500)));
	int i = 0;

	public void postStake(Long betOfferId, Bet bet) {
		Logger.debug("addStake method start");

		bet.setBetOfferId(betOfferId);
		stakes.add(bet);

		Logger.info(stakes.toString());
		Logger.debug("addStake method end");
	}

	public List<Bet> getStakes() {

		Logger.debug("getStakes method start");
		Logger.info(stakes.toString());

		return stakes;
	}

	public ArrayList<String> getHighStakes(Long betOfferId) {
		ArrayList<String> sortedFormattedlist = new ArrayList<>();
		Logger.debug("getHighStakes method start");

		// Grouping the stakes based on betofferid and customer

		List<Bet> highstakes = stakes.stream().filter(x -> x.getBetOfferId() == betOfferId)
				.collect(Collectors.toMap(Bet::getCustomerId, Function.identity(),
						BinaryOperator.maxBy(Comparator.comparingLong(Bet::getStake))))
				.values().parallelStream().collect(Collectors.groupingBy(Bet::getStake)).values().parallelStream()
				.flatMap(x -> x.stream().limit(20)).collect(Collectors.toList());

		Logger.debug("highstakes list size");
		Logger.info(highstakes.size());
		Logger.info(highstakes.toString());

		// Sorting the List based on highest stakes and limit the results
		List<Bet> sortedList = highstakes.parallelStream().sorted(Comparator.comparingLong(Bet::getStake).reversed())
				.limit(20).collect(Collectors.toList());
		Logger.info("sortedStream" + sortedList.toString());

		// Formatting the sorted stakes list eg: 1234=4500,57453=1337
		sortedList.stream().collect(Collectors.toList()).forEach(value -> {

			sortedFormattedlist.add(sortedList.get(i).customerId + "=" + sortedList.get(i).getStake());
			i = i + 1;
		});
		Logger.debug("sortedFormattedlist size");
		Logger.info(sortedFormattedlist.size());
		Logger.info(sortedFormattedlist.toString());
		i = 0;
		Logger.debug("getHighStakes method end");

		return sortedFormattedlist;
	}

	public boolean validSession(String sessionkey, HttpServletRequest httpServletRequest) {
		Logger.debug("sessionkey ::" + httpServletRequest.getRequestedSessionId());

		if (httpServletRequest.getRequestedSessionId().equals(sessionkey)) {

			return true;
		}
		Logger.debug("Invalid Session");
		return false;
	}

}
