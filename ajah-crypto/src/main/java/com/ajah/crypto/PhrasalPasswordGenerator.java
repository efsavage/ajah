/*
 *  Copyright 2013-2014 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.crypto;

import com.ajah.util.RandomUtils;

/**
 * Generates a random but memorable phrase to use as a password.
 * 
 * @author <a href="Http://efsavage.com">Eric F. Savage</a>, <a
 *         href="Mailto:code@efsavage.com">code@efsavage.com</a>.
 */
public class PhrasalPasswordGenerator {

	private static final String[] ADJECTIVES = new String[] { "Able", "Adorable", "Acrobatic", "Active", "Adept", "Agile", "Agreeable", "Alert", "All", "Ambitious", "Amusing", "Ancient", "Angelic",
			"Angry", "Animated", "Anxious", "Artistic", "Athletic", "Attentive", "Attractive", "Authentic", "Average", "Awesome", "Awful", "Awkward", "Bad", "Basic", "Better", "Big", "Blue",
			"Blushing", "Bold", "Boring", "Bossy", "Both", "Bouncy", "Brave", "Bright", "Brilliant", "Bronze", "Bulky", "Bumpy", "Burly", "Busy", "Calm", "Careful", "Careless", "Caring", "Charming",
			"Cheap", "Cheerful", "Classic", "Clean", "Clever", "Clueless", "Clumsy", "Cold", "Colorful", "Colossal", "Common", "Complex", "Confused", "Content", "Cool", "Corny", "Crafty", "Crazy",
			"Creative", "Crushing", "Cute", "Dapper", "Daring", "Dazzling", "Deep", "Dense", "Devoted", "Different", "Difficult", "Digital", "Dim", "Dimpled", "Direct", "Dizzy", "Double", "Dry",
			"Dull", "Eager", "Earnest", "Early", "Easy", "Educated", "Elastic", "Elderly", "Electric", "Elegant", "Emotional", "Enormous", "Equal", "Even", "Excellent", "Exotic", "Expensive",
			"Expert", "Fair", "Faithful", "Fake", "Famous", "Fancy", "Faraway", "Fast", "Favorite", "Fickle", "Fine", "Flaky", "Flashy", "Flat", "Fluffy", "Formal", "Free", "Fresh", "Frozen",
			"Frugal", "Funny", "Fuzzy", "Gentle", "Giant", "Giddy", "Gifted", "Glass", "Glossy", "Golden", "Good", "Graceful", "Grand", "Gray", "Great", "Green", "Handsome", "Handy", "Happy",
			"Harmless", "Hasty", "Healthy", "Helpful", "Hidden", "Honest", "Hopeful", "Hot", "Huge", "Humble", "Hungry", "Husky", "Icy", "Idle", "Immense", "Infinite", "Jolly", "Jovial", "Joyful",
			"Joyous", "Jumbo", "Jumpy", "Kind", "Large", "Late", "Lean", "Light", "Little", "Lively", "Loud", "Lovely", "Loving", "Low", "Loyal", "Lucky", "Major", "Massive", "Mature", "Mellow",
			"Merry", "Mild", "Minty", "Misty", "Modern", "Modest", "Muddy", "Natural", "Nautical", "Nice", "Nifty", "Nimble", "Noisy", "Normal", "Nutty", "Obvious", "Orange", "Original", "Other",
			"Our", "Oval", "Peaceful", "Perfect", "Perky", "Pink", "Plain", "Plastic", "Playful", "Plump", "Polite", "Posh", "Positive", "Powerful", "Practical", "Precious", "Pretty", "Proper",
			"Proud", "Punctual", "Pure", "Purple", "Quick", "Quiet", "Quirky", "Radiant", "Ragged", "Rapid", "Real", "Red", "Regal", "Regular", "Remote", "Rich", "Rosy", "Round", "Rowdy", "Royal",
			"Salty", "Sandy", "Second", "Secret", "Serious", "Sharp", "Shiny", "Short", "Shy", "Silent", "Silly", "Silver", "Simple", "Single", "Skinny", "Sleepy", "Slim", "Small", "Smart", "Smooth",
			"Smug", "Snappy", "Sneaky", "Soft", "Solid", "Somber", "Speedy", "Spicy", "Spiffy", "Square", "Squeaky", "Stable", "Standard", "Stiff", "Strange", "Steel", "Strict", "Strong", "Stunning",
			"Sturdy", "Stylish", "Subtle", "Sunny", "Super", "Sweet", "Swift", "Tall", "Tame", "Tan", "Terrific", "These", "Thick", "Thin", "Thirsty", "Those", "Thoughtful", "Thrifty", "Tidy",
			"Tight", "Timely", "Tinted", "Tiny", "Tired", "Tough", "Trained", "Tricky", "Trim", "Trivial", "True", "Trusty", "Truthful", "Twin", "United", "Unusual", "Upbeat", "Upright", "Urban",
			"Useful", "Valid", "Valuable", "Vibrant", "Victorious", "Vigilant", "Vigorous", "Violet", "Virtual", "Visible", "Vital", "Vivid", "Warm", "Watchful", "Wavy", "Wealthy", "Wet", "Wiggly",
			"Wild", "Windy", "Wise", "Witty", "Wooden", "Worldly", "Worthy" };
	private static final String[] NOUNS = new String[] { "Dalmatians", "Robins", "Alligators", "Alpacas", "Amphibians", "Anacondas", "Angelfish", "Animals", "Ants", "Anteaters", "Antelopes", "Apes",
			"Aphids", "Armadillos", "Baboons", "Badgers", "Bandicoots", "Barnacles", "Barracudas", "Bats", "Beagles", "Bears", "Beavers", "Bees", "Beetles", "Birds", "Bisons", "Bloodhounds",
			"Blowfish", "Bobcats", "Boxers", "Buffalos", "Bugs", "Bulls", "Bulldogs", "Bullfrogs", "Bumblebees", "Butterflys", "Camels", "Canarys", "Cardinals", "Caribous", "Cats", "Caterpillars",
			"Cattle", "Centipedes", "Chameleons", "Cheetahs", "Chickadees", "Chickens", "Chipmunks", "Clams", "Clownfish", "Cobras", "Cougars", "Cows", "Coyotes", "Crabs", "Cranes", "Crayfish",
			"Crickets", "Crocodiles", "Crows", "Deers", "Dinosaurs", "Dogs", "Dolphins", "Donkeys", "Doves", "Dragons", "Dragonflys", "Ducks", "Eagles", "Earthworms", "Eels", "Elephants", "Elks",
			"Emus", "Falcons", "Ferrets", "Finches", "Fireflys", "Fish", "Flamingos", "Flounders", "Foxes", "Gazelles", "Geckos", "Gerbils", "Giraffes", "Goats", "Geese", "Gophers", "Gorillas",
			"Grasshoppers", "Gulls", "Hamsters", "Hawks", "Hedgehogs", "Hens", "Herons", "Herrings", "Hippos", "Hornets", "Horses", "Hounds", "Hyenas", "Iguanas", "Impalas", "Insects", "Jaguars",
			"Jellyfish", "Kangaroos", "Koalas", "Ladybugs", "Lemurs", "Leopards", "Lions", "Lizards", "Llamas", "Lobsters", "Locusts", "Loons", "Lynxes", "Macaws", "Mackerel", "Mammals", "Mammoths",
			"Manatees", "Mandrills", "MantaRays", "Mantiss", "Mastiffs", "Mastodons", "Meadowlarks", "Mealworms", "Meerkats", "Mices", "Minks", "Minnows", "Mockingbirds", "Mongooses", "Moose",
			"Moths", "MountainLions", "Mouses", "Muskrats", "Narwhals", "Newts", "Nightingales", "Nuthatches", "Octopi", "Ostriches", "Otters", "Owls", "Oxen", "Oysters", "Pandas", "Panthers",
			"Parakeets", "Parrots", "Pelicans", "Penguins", "Pigeons", "Platypuses", "Poodles", "Porcupines", "Porpoises", "Primates", "Puffins", "Pumas", "Pythons", "Quails", "Rabbits", "Raccoons",
			"Rats", "Reptiles", "Rhinos", "Roaches", "Roadrunners", "Robins", "Rodents", "Roosters", "Salmon", "Scallops", "SeaLions", "Seals", "Sharks", "Sheep", "Shepherds", "Shrimps", "Skunks",
			"Sloths", "Snails", "Snakes", "Sparrows", "Spiders", "Squid", "Squirrels", "Starfish", "Starlings", "Storks", "Sunfish", "Swans", "Swordfish", "Tadpoles", "Terns", "Terriers", "Tigers",
			"Toads", "Tortoises", "Toucans", "Treefrogs", "Trouts", "Tuna", "Turkeys", "Turtles", "Vipers", "Vultures", "Wallabies", "Walruses", "Wasps", "Waterbugs", "Whales", "Wolves",
			"Wolverines", "Wombats", "Woodchucks", "Woodpeckers", "Worms", "Wrens", "Yaks" };
	private static final String[] VERBS = new String[] { "Act", "Agree", "Amuse", "Analyze", "Announce", "Answer", "Appear", "Applaud", "Approve", "Argue", "Arrange", "Arrive", "Ask", "Attack",
			"Auction", "Babble", "Balance", "Bargain", "Barter", "Bathe", "Battle", "Beg", "Begin", "Behave", "Believe", "Bellow", "Bend", "Bet", "Bid", "Bite", "Blink", "Blush", "Boast", "Bob",
			"Bolt", "Borrow", "Bounce", "Bow", "Box", "Brag", "Breathe", "Build", "Bump", "Buy", "Buzz", "Camp", "Care", "Carry", "Carve", "Catch", "Challenge", "Change", "Chant", "Charge", "Chase",
			"Cheer", "Chew", "Chomp", "Choose", "Chop", "Claim", "Clap", "Clean", "Clear", "Climb", "Clip", "Close", "Coach", "Collect", "Color", "Comb", "Command", "Comment", "Compare", "Compete",
			"Complain", "Complete", "Confess", "Cook", "Copy", "Correct", "Count", "Crash", "Crave", "Crawl", "Cross", "Cry", "Curse", "Curve", "Cut", "Cycle", "Dance", "Deal", "Debate", "Decide",
			"Declare", "Act", "Agree", "Amuse", "Analyze", "Announce", "Answer", "Appear", "Applaud", "Approve", "Argue", "Arrange", "Arrive", "Ask", "Attack", "Auction", "Babble", "Balance",
			"Bargain", "Barter", "Bathe", "Battle", "Beg", "Begin", "Behave", "Believe", "Bellow", "Bend", "Bet", "Bid", "Bite", "Blink", "Blush", "Boast", "Bob", "Bolt", "Borrow", "Bounce", "Bow",
			"Box", "Brag", "Breathe", "Build", "Bump", "Buy", "Buzz", "Camp", "Care", "Carry", "Carve", "Catch", "Challenge", "Change", "Chant", "Charge", "Chase", "Cheer", "Chew", "Chomp", "Choose",
			"Chop", "Claim", "Clap", "Clean", "Clear", "Climb", "Clip", "Close", "Coach", "Collect", "Color", "Comb", "Command", "Comment", "Compare", "Compete", "Complain", "Complete", "Confess",
			"Cook", "Copy", "Correct", "Count", "Crash", "Crave", "Crawl", "Cross", "Cry", "Curse", "Curve", "Cut", "Cycle", "Dance", "Deal", "Debate", "Decide", "Declare" };
	private static final String[] ADVERBS = new String[] { "Anywhere", "Around", "Away", "Boldly", "Bravely", "Briskly", "Calmly", "Carefully", "Casually", "Cheerfully", "Cleanly", "Comfortably",
			"Completely", "Continually", "Correctly", "Curiously", "Currently", "Daily", "Daringly", "Decently", "Deeply", "Defiantly", "Deftly", "Delicately", "Densely", "Diagonally", "Differently",
			"Diligently", "Directly", "Eagerly", "Early", "Earnestly", "Easily", "Efficiently", "Elegantly", "Elsewhere", "Endlessly", "Enjoyably", "Enough", "Equally", "Even", "Evenly", "Evermore",
			"Everywhere", "Exactly", "Fairly", "Faithfully", "Famously", "Far", "Fast", "Favorably", "Fluently", "Fondly", "Forever", "Formally", "Formerly", "Forward", "Frankly", "Freely", "Fully",
			"Furiously", "Gently", "Gladly", "Gleefully", "Gracefully", "Graciously", "Gradually", "Gratefully", "Greatly", "Handily", "Handsomely", "Happily", "Hastily", "Heavily", "Helpfully",
			"Hence", "Highly", "Honestly", "Horizontally", "Hourly", "Impatiently", "Indoors", "Instantly", "Intensely", "Joyfully", "Joyously", "Justly", "Keenly", "Kindly", "Knowingly", "Late",
			"Lately", "Later", "Less", "Lightly", "Likely", "Lively", "Loudly", "Lovingly", "Loyally", "Luckily", "Magically", "Meaningfully", "Merrily", "Mightily", "Monthly", "More", "Mostly",
			"Much", "Naturally", "Nearby", "Neatly", "Next", "Nicely", "Nightly", "Normally", "Now", "Obviously", "Oddly", "Often", "Once", "Openly", "Orderly", "Outdoors", "Overseas", "Patiently",
			"Perfectly", "Plainly", "Playfully", "Poetically", "Politely", "Powerfully", "Presumably", "Prettily", "Previously", "Principally", "Promptly", "Properly", "Proudly", "Punctually",
			"Quicker", "Quickly", "Quietly", "Randomly", "Rapidly", "Rarely", "Readily", "Really", "Reasonably", "Reassuringly", "Recently", "Recklessly", "Regularly", "Reliably", "Remarkably",
			"Repeatedly", "Reponsibly", "Respectably", "Restfully", "Richly", "Rightly", "Rigidly", "Roughly", "Routinely", "Rudely", "Safely", "Securely", "Seldom", "Sharply", "Shortly", "Silently",
			"Simply", "Skillfully", "Sleepily", "Slightly", "Slowly", "Slyly", "Smoothly", "Softly", "Solidly", "Somehow", "Sometimes", "Somewhat", "Somewhere", "Soon", "Specially", "Speedily",
			"Steadily", "Sternly", "Still", "Stressfully", "Strictly", "Stylishly", "Suddenly", "Suitably", "Supremely", "Surely", "Sweetly", "Swiftly", "Tenderly", "Thankfully", "Then", "There",
			"Thoroughly", "Tightly", "Today", "Together", "Tomorrow", "Too", "Totally", "Touchingly", "Truly", "Truthfully", "Twice", "Uniformly", "Up", "Upright", "Upward", "Urgently", "Usefully",
			"Usually", "Vertically", "Visibly", "Visually", "Warmly", "Weekly", "Well", "Where", "Wholly", "Wildly", "Willfully", "Willingly", "Wisely", "Yearly", "Yesterday", "Youthfully",
			"Zestfully" };

	public static long getPermutations() {
		return (long) ADJECTIVES.length * NOUNS.length * VERBS.length * ADVERBS.length;

	}

	/**
	 * Returns a random phrase in the form of {Adjective}{Noun}{Verb}{Adverb}.
	 * Example:
	 * 
	 * SkinnyDucksRunBoldly
	 * 
	 * @return A random phrase.
	 */
	public static String getRandomPassword() {
		return RandomUtils.getRandomElement(ADJECTIVES) + RandomUtils.getRandomElement(NOUNS) + RandomUtils.getRandomElement(VERBS) + RandomUtils.getRandomElement(ADVERBS);
	}
}
