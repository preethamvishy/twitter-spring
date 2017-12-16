package com.example.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.CursoredList;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 *
 * @author Preetham Viswanathan 
 */

@RestController
@CrossOrigin(origins = "*")

@RequestMapping("/")
public class TwitterController extends DefaultResponseErrorHandler {


	private Twitter twitter;
	private ConnectionRepository connectionRepository;
	
	/**Comment following lines to use your Twitter tokens declared in TwitterApplication.java**/
	@Inject
	public TwitterController(Twitter twitter, ConnectionRepository connectionRepository) {
	    this.twitter = twitter;
	    this.connectionRepository = connectionRepository;
	}
	
	/**Uncomment following lines to autowire Twitter tokens**/
	//	@Autowired
	//	private Twitter twitter;
	
	//	home timeline will only work if you have added your tokens
	@RequestMapping(value= "home")
	public List<Tweet> getHomeTimeline() {
		return twitter.timelineOperations().getHomeTimeline(200);
	}
	
	@RequestMapping(value= "user/{username}")
	public List<Tweet> getUserTweets(@PathVariable final String username) {
		return twitter.timelineOperations().getUserTimeline(username, 200);
	}
	
	@RequestMapping(value= "favs/{username}")
	public List<Tweet> getUserFavs(@PathVariable final String username) {
		return twitter.timelineOperations().getFavorites(username, 200);
	}
	
	@RequestMapping(value= "id/{tweetId}")
	public List<Tweet> getTweetById(@PathVariable final String tweetId) {
		
		long id = Long.parseLong(tweetId);
		List<Tweet> tweets = new ArrayList<Tweet>();
		tweets.add(twitter.timelineOperations().getStatus(id));
		return  tweets;
	}
	
	@RequestMapping(value= "list/{listIdentifier}")
	//listIdentifier is of the form:    {username}/{list_slug}
	public List<Tweet> getListTweets(@PathVariable final String listIdentifier) {
		
		String username = listIdentifier.substring(0, listIdentifier.indexOf('/'));
		String slug = listIdentifier.substring(listIdentifier.indexOf('/')+1);
		
		List<Tweet> tweets = new ArrayList<Tweet>();
		tweets = twitter.listOperations().getListStatuses(username, slug, 200);
		return  tweets;
	}
	
	@RequestMapping(value= "following/{userName}")
	public CursoredList<TwitterProfile> getFollowing(@PathVariable final String userName) {		
		return twitter.friendOperations().getFriends(userName);
	}
	
	@RequestMapping(value= "followers/{userName}")
	public CursoredList<TwitterProfile> getFollowers(@PathVariable final String userName) {		
		return twitter.friendOperations().getFollowers(userName);
	}
	
	@RequestMapping(value="/addToList/{listAddToken}")
	//listAddToken is of the form:     {list_slug}/{username}
	public Map<String, String> addToList(@PathVariable final String listAddToken)
	{	
		String slug = listAddToken.substring(0, listAddToken.indexOf('/'));
		String username = listAddToken.substring(listAddToken.indexOf('/')+1);
		
		long listId = twitter.listOperations().getList(twitter.userOperations().getScreenName(), slug).getId();
		twitter.listOperations().addToList(listId, username);
		return Collections.singletonMap("statusText", "OK");
	}
	
}

