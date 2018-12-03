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
		// TODO Auto-generated method stub'
		Logger.debug("addStake method start");

		bet.setBetOfferId(betOfferId);
		stakes.add(bet);

		Logger.info(stakes.toString());
		Logger.debug("addStake method end");
	}

	public List<Bet> getStakes() {
		// TODO Auto-generated method stub

		Logger.debug("getStakes method start");
		Logger.info(stakes.toString());

		return stakes;
	}

	public ArrayList<String> getHighStakes(Long betOfferId) {
		// TODO Auto-generated method stub
		Logger.debug("getHighStakes method start");

		List<Bet> highstakes = stakes.stream().filter(x -> x.getBetOfferId() == betOfferId)
				.collect(Collectors.toMap(Bet::getCustomerId, Function.identity(),
						BinaryOperator.maxBy(Comparator.comparingLong(Bet::getStake))))
				.values().stream().collect(Collectors.groupingBy(Bet::getStake)).values().stream()
				.flatMap(x -> x.stream().limit(20)).collect(Collectors.toList());

		Logger.debug("highstakes list size");
		Logger.info(highstakes.size());
		Logger.debug("getHighStakes method end");
		Logger.info(highstakes.toString());

		ArrayList<String> highstakesformattedlist = new ArrayList<>();

		highstakes.stream().limit(20).collect(Collectors.toList()).forEach(value -> {
			highstakesformattedlist.add(highstakes.get(i).customerId + "=" + highstakes.get(i).getStake());
			i = i + 1;
		});
		Logger.debug("highstakesformatted list size");
		Logger.info(highstakesformattedlist.size());
		i = 0;
		return highstakesformattedlist;
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
