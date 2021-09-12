package com.beta.fantasytm;

import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.PlayerRepository;
import com.beta.fantasytm.data.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FantasytmApplication {

	public static void main(String[] args) {
		SpringApplication.run(FantasytmApplication.class, args);
	}


	@Bean
	public CommandLineRunner dataLoader(PlayerRepository repo, BuffRepository buffRepo, TeamRepository teamRepo) {
		return new CommandLineRunner() {
			@Override
			public void run(String... args) throws Exception {
				/*
				ISSUES

				TODO:
				- add chips
				- FINISH THE ADMIN HUB TO TAKE INTO ACCOUNT BUFFS, ALSO TEST IT

				TRANSFERS;
				- make all players have int popularity, it will:
					* increase by one for each buy (not counting team creation)
				 	* decrease by one for each sell
				- transfers can be made in the team hubs
				- only one transfer can be made per week

				PLANNED FOR LATER
				CHIPS;
				- any team can have a chip active
				- chip will provide various bonuses
				- chips available
					* BOOST PLAYER (give 2x points to a player for this week, does not stack)
					* CAPTAIN BUFF (3x points instead of 2x)
				- tie chips to Wallets
				*/

				// Buff save
				buffRepo.save(new Buff(1L, BuffType.NULL, "NONE ACTIVE", "No buff is active"));
				buffRepo.save(new Buff(2L, BuffType.FIRE_WEEK, "FIRE WEEK", "Regular players all score 1.5x"));
				buffRepo.save(new Buff(3L, BuffType.TRIPLE_CAPTAIN, "TRIP CAPTAIN", "Your captain will score 1.5x"));
				buffRepo.save(new Buff(4L, BuffType.QUAD_UNDERDOG, "QUAD UNDER", "Your underdog will score 4x"));


				// ABSOLUTELY FUCKING DISGUSTING
				// But honestly, I don't see any better way of doing this if different roles need different points
				// Only solution is maybe standard cost could be modified by the points modifier
				// NOTE: Calling save() on something with identical ID will overwrite
				repo.save(new Player(1L, "CarlJr", "SOLARY", 100, 20, 100, 0, Position.CAPTAIN));
				repo.save(new Player(2L, "Pac", "MNM", 100, 20, 100, 0, Position.CAPTAIN));
				repo.save(new Player(3L, "Aurel", "GAMINGPRIVE", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(4L, "Gwen", "GAMEWARD", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(5L, "Affi", "BDS", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(6L, "Mudda", "EDELWEISS", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(7L, "Bren", "KARMINE CORP", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(8L, "Papou", "GAMERS ORIGIN", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(9L, "Spam", "ALLIANCE", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(10L, "Massa", "BIG", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(11L, "Kappa", "MCLAREN SHADOW", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(12L, "tween", "ORGLESS", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(13L, "riolu", "NORDAVIND", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(14L, "Scrapie", "LAZARUS", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(15L, "evoN", "TEAM SECRET", 100, 20, 100,0, Position.CAPTAIN));
				repo.save(new Player(16L, "link", "GRIZI ESPORT", 100, 20, 100,0, Position.CAPTAIN));

				repo.save(new Player(17L, "CarlJr", "SOLARY", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(18L, "Pac", "MNM", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(19L, "Aurel", "GAMINGPRIVE", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(20L, "Gwen", "GAMEWARD", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(21L, "Affi", "BDS", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(22L, "Mudda", "EDELWEISS", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(23L, "Bren", "KARMINE CORP", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(24L, "Papou", "GAMERS ORIGIN", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(25L, "Spam", "ALLIANCE", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(26L, "Massa", "BIG", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(27L, "Kappa", "MCLAREN SHADOW", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(28L, "tween", "ORGLESS", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(29L, "riolu", "NORDAVIND", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(30L, "Scrapie", "LAZARUS", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(31L, "evoN", "TEAM SECRET", 100, 20, 100,0, Position.REGULAR));
				repo.save(new Player(32L, "link", "GRIZI ESPORT", 100, 20, 100,0, Position.REGULAR));

				repo.save(new Player(33L, "Spam", "ALLIANCE", 100, 20, 100,0, Position.UNDERDOG));
				repo.save(new Player(34L, "Massa", "BIG", 100, 20, 100,0, Position.UNDERDOG));
				repo.save(new Player(35L, "Kappa", "MCLAREN SHADOW", 100,20, 100,0, Position.UNDERDOG));
				repo.save(new Player(36L, "tween", "ORGLESS", 100, 20, 100,0, Position.UNDERDOG));
				repo.save(new Player(37L, "riolu", "NORDAVIND", 100, 20, 100,0, Position.UNDERDOG));
				repo.save(new Player(38L, "Scrapie", "LAZARUS", 100, 20, 100,0, Position.UNDERDOG));
				repo.save(new Player(39L, "evoN", "TEAM SECRET", 100, 20, 100,0, Position.UNDERDOG));
				repo.save(new Player(40L, "link", "GRIZI ESPORT", 100, 20, 100,0, Position.UNDERDOG));
			}
		};
	}
}
