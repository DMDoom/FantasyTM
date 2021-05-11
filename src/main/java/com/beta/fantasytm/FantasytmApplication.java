package com.beta.fantasytm;

import com.beta.fantasytm.data.BuffRepository;
import com.beta.fantasytm.data.PlayerRepository;
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
	public CommandLineRunner dataLoader(PlayerRepository repo, BuffRepository buffRepo) {
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
				buffRepo.save(new Buff(1L, BuffType.NULL));
				buffRepo.save(new Buff(2L, BuffType.FIRE_WEEK));
				buffRepo.save(new Buff(3L, BuffType.TRIPLE_CAPTAIN));
				buffRepo.save(new Buff(4L, BuffType.QUAD_UNDERDOG));


				// ABSOLUTELY FUCKING DISGUSTING
				// But honestly, I don't see any better way of doing this if different roles need different points
				// Only solution is maybe standard cost could be modified by the points modifier
				// NOTE: Calling save() on something with identical ID will overwrite
				repo.save(new Player(1L, "CarlJr", "SOLARY", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(2L, "Pac", "MNM", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(3L, "Aurel", "GAMINGPRIVE", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(4L, "Gwen", "GAMEWARD", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(5L, "Affi", "BDS", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(6L, "Mudda", "EDELWEISS", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(7L, "Bren", "KARMINE CORP", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(8L, "Papou", "GAMERS ORIGIN", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(9L, "Spam", "ALLIANCE", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(10L, "Massa", "BIG", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(11L, "Kappa", "MCLAREN SHADOW", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(12L, "tween", "ORGLESS", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(13L, "riolu", "NORDAVIND", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(14L, "Scrapie", "LAZARUS", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(15L, "evoN", "TEAM SECRET", 1000, 20, 600, Position.CAPTAIN));
				repo.save(new Player(16L, "link", "GRIZI ESPORT", 1000, 20, 600, Position.CAPTAIN));

				repo.save(new Player(17L, "CarlJr", "SOLARY", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(18L, "Pac", "MNM", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(19L, "Aurel", "GAMINGPRIVE", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(20L, "Gwen", "GAMEWARD", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(21L, "Affi", "BDS", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(22L, "Mudda", "EDELWEISS", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(23L, "Bren", "KARMINE CORP", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(24L, "Papou", "GAMERS ORIGIN", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(25L, "Spam", "ALLIANCE", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(26L, "Massa", "BIG", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(27L, "Kappa", "MCLAREN SHADOW", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(29L, "tween", "ORGLESS", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(30L, "riolu", "NORDAVIND", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(31L, "Scrapie", "LAZARUS", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(32L, "evoN", "TEAM SECRET", 1000, 20, 250, Position.REGULAR));
				repo.save(new Player(33L, "link", "GRIZI ESPORT", 1000, 20, 250, Position.REGULAR));

				repo.save(new Player(35L, "Spam", "ALLIANCE", 1000, 20, 300, Position.UNDERDOG));
				repo.save(new Player(36L, "Massa", "BIG", 1000, 20, 300, Position.UNDERDOG));
				repo.save(new Player(37L, "Kappa", "MCLAREN SHADOW", 1000,20, 300, Position.UNDERDOG));
				repo.save(new Player(38L, "tween", "ORGLESS", 1000, 20, 300, Position.UNDERDOG));
				repo.save(new Player(39L, "riolu", "NORDAVIND", 1000, 20, 300, Position.UNDERDOG));
				repo.save(new Player(40L, "Scrapie", "LAZARUS", 1000, 20, 300, Position.UNDERDOG));
				repo.save(new Player(41L, "evoN", "TEAM SECRET", 1000, 20, 300, Position.UNDERDOG));
				repo.save(new Player(42L, "link", "GRIZI ESPORT", 1000, 20, 300, Position.UNDERDOG));
			}
		};
	}
}
