package com.kambhi.betapi;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kambhi.betapi.exception.BetApplicationException;
import com.kambhi.betapi.logger.Logger;

/**
 * @author Riju
 *
 */

@RestController
public class BetApplicationController {

	@Autowired
	private BetApplicationService betApplicationService;

	/**
	 * create unique session key as a string valid for 10 minutes
	 * 
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("{customerId}/session")

	public String createSession(HttpServletRequest httpServletRequest,HttpSession session) {

		Logger.debug("sessionkey from request::" + httpServletRequest.getRequestedSessionId());
		Logger.debug("sessionkey from session ::" + session.getId());
		return session.getId();
		//return httpServletRequest.getRequestedSessionId();

	}

	/**
	 * Post a Customer Stake on Offer
	 * 
	 * @param sessionkey
	 * @param betOfferId
	 * @param bet
	 * @param httpServletRequest
	 */
	@RequestMapping(method = RequestMethod.POST, value = "{betOfferId}/stake")

	public @ResponseBody Resource<Bet> postStake(@RequestParam("sessionkey") String sessionkey,
			@PathVariable Long betOfferId, @RequestBody Bet bet, HttpServletRequest httpServletRequest) {
		Resource<Bet> resource = new Resource<Bet>(bet);
		if (betApplicationService.validSession(sessionkey, httpServletRequest)) {
			betApplicationService.postStake(betOfferId, bet);
			
			//adding hateoas links for easy navigation
			resource = new Resource<Bet>(bet);
			ControllerLinkBuilder linkTo = linkTo(
					methodOn(this.getClass()).getHighestStakes(sessionkey, betOfferId, httpServletRequest));
			resource.add(linkTo.withRel("{betOfferId}/highstakes"));
		}
		return resource;

	}

	/**
	 * Retrieve all the stakes (Optional)
	 * 
	 * @return
	 */
	@RequestMapping("/stakes")

	public List<Bet> getStakes() {
		try {
		throw new BetApplicationException("testin exception");
		}catch(RuntimeException e) {
			System.out.println("Do notin");
		}
		return betApplicationService.getStakes();
	}

	/**
	 * Retrieve high stakes for a specific bet offer
	 * 
	 * @param sessionkey
	 * @param betOfferId
	 * @param httpServletRequest
	 * @return
	 */
	@RequestMapping("{betOfferId}/highstakes")

	public @ResponseBody ArrayList<String> getHighestStakes(@RequestParam("sessionkey") String sessionkey,
			@PathVariable Long betOfferId, HttpServletRequest httpServletRequest) {

		 if (betApplicationService.validSession(sessionkey, httpServletRequest)) {
		return betApplicationService.getHighStakes(betOfferId);
		 } else {
		 return new ArrayList<String>();
		 }
	}

}