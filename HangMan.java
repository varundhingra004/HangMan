import java.util.*;
import java.io.*;

/*
 * Information of all the players like Name,score and number are encapsulated 
 * into a single class called Player. All operations are then performed on
 * instances of these classes.
 */
class Player{
	int player_number;
	String player_name;
	int player_score;
/*
 * The following parameterized constructor takes care of assigning values to 
 * the attributes oof each player. Player names are taken from user inout in 
 * Command line argument and their position is equivalent to the index of the 
 * Command line array. All scores are initially set ti 0.
 */

	Player(String name,int no)
	{
		player_number = no;
		player_name = name;
		player_score = 0;
	}
}

public class HangMan
{
/*
 * In order to dipsplay the top 4 players with the higjest scores, we sort
 * through the player list Selection sort. This function returns an array of 
 * sorted players to the main(). main() then prints the top 4 players.
 */
	static Player temp_variable;//Temporary variable used for sorting

	public static Player[] sort_players(Player player_list[])
	{
		for (int i = 0;i<player_list.length;i++)
			{
				for (int j = (i+1); j<player_list.length;j++)
				{
					if (player_list[i].player_score<player_list[j].player_score)
					{
						temp_variable = player_list[i];
						player_list[i] = player_list[j];
						player_list[j] = temp_variable;
					}
				}
			}	

			return player_list;

	}
/*
 * The playGame() method accespts the Player array and a word from the file,
 * takes user input and prints the right/wrong answers. playGame() will call 
 * the getWords() function which provides the input word for playGame()
 */
	public static void playGame(Player player_info,String word) 
	{
		//Converting string word to blanks
		String guess_letter="";//Used for user input.
		int guess_counter = 1;//This is a counter variable for no of guesses.
		/*
		 * got_answer is a flag variable that determines of the player was able
		 * to guess correctly or not. Based on this, the score of the player is
		 * determined.
		 */
		int got_answer = 0;
		String wrong_guesses = "";//To display incorrect guesses.
		
		/*Calculate the length of the strings and display equivalent number of
		 * blanks.
		 */
		char blanks[] = new char[word.length()];
		for (int letter_counter = 0;letter_counter<blanks.length;letter_counter++)
			blanks[letter_counter]='-';
				
		StringBuilder blank_word = new StringBuilder(String.valueOf(blanks));
		Scanner user_input = new Scanner(System.in);

		while(guess_counter <= 8)
		{
			int correct_guess = 0;//Flag variable to validate each guess.
			
			System.out.println("\n");
			System.out.println("You have "+(9 - guess_counter)+" guesses left\n");
			System.out.println(blank_word);
			System.out.println("\nWrong Guesses = "+wrong_guesses);
			System.out.println("Enter the letter, If you enter more than 1 letter, only the first letter will be considered");
			guess_letter = ((user_input.nextLine()).charAt(0))+"";
/*
 * The word from file is traversed character by character and compared to the 
 * input letter. 
 */
			for(int letter_counter = 0;letter_counter<blank_word.length();letter_counter++)
				{
					if((blank_word.charAt(letter_counter)+"").equalsIgnoreCase(guess_letter))
						{
							System.out.println("You have already guessed this letter correctly, please choose another");
							correct_guess = 1;
							break;
						}

					if((word.charAt(letter_counter)+"").equalsIgnoreCase(guess_letter))
						{
							blank_word.replace(letter_counter,letter_counter+1,guess_letter);
							correct_guess = 1;
						}

				}

			if(correct_guess == 0)
				{
					wrong_guesses+=guess_letter;
					guess_counter++;
				}
/*
 * Player scoring is handeled here. The score assigned is dependant on the
 * length of the word that was chosen randomly from the word file.
 * 
 * If the correct answer has been guessed, then scores are assigned as follows:
 * 					1) If the length of the word is between 1 and 3, score is
 * 							increnented by 5
 * 					2) If the length of the word is between 3 and 10, the score
 * 							is incremented by 10
 * 					3)If the length of the word is more than 10, the score is
 * 								incremented by 20.
 */
			if (word.equalsIgnoreCase(blank_word+""))
				{
					if(word.length()<=3)
						player_info.player_score+=5;
					else if(word.length()>3 && word.length()<=10)
						player_info.player_score+=10;
					else if(word.length()>10)
						player_info.player_score+=20;

					System.out.println("The correct anser is : \t"+word);
					got_answer = 1;
					break;
				}

		}
		
/*If the correct answer has been guessed, then scores are assigned as follows:
 * 					1) If the length of the word is between 1 and 3, score is
 * 							decremented by 20
 * 					2) If the length of the word is between 3 and 10, the score
 * 							is decremented by 10
 * 					3)If the length of the word is more than 10, the score is
 * 							decremented by 0.
*/
		if(got_answer == 0)
		{
			if(word.length()<=3)
				player_info.player_score-=20;
			else if(word.length()>3 && word.length()<=10)
				player_info.player_score-=10;
			else
				player_info.player_score-=0;
			System.out.println("The correct anser is : \t"+word);
		}
	}

/*
 * getWords() uses File handling operations to get a random word from the file,
 * and returns the word.The playGame() method calls getWords() and thus uses
 * the word returned by getWords().
 */
	public static String getWords()
	{
		String filename = "/usr/share/dict/words";
		String line = "";
/*
 * RandomAcccessFile will open the file in read(r) mode to enable random access
 * to the file.
 * 
 */
		try{
		RandomAccessFile file = new RandomAccessFile(filename,"r");
		Random random_position = new Random();
		
		while(line.length()<1)
		{
			/*
			 * seek method will place the cursor in a randomly allocated
			 * position.
			 */
			file.seek(random_position.nextInt((int)file.length()));
			line = file.readLine();//random word from file is stored in line.
		}

		file.close();
	}

		catch(FileNotFoundException ex)
		{
			System.out.println("File NOT Found !!!!");
		}

		catch(IOException ex)
		{
			System.out.println("ERROR reading file");
		}


		return line;
	}

	public static void main(String args[])
	{
/*
 *player_list[] is an array variable of type Player. It holds all Player objects
 * All player information(name,score and number) is stored in this array.
 * This concept is called array of objects.
*/
		Player player_list[] = new Player[args.length];

		for (int i = 0;i<args.length;i++)
			player_list[i] = new Player(args[i],(i+1)); //initializing the players attained from commapnd line
/*
 * We will iterate through each player and call the playGame() method for each.
 */
		for (int i = 0; i<player_list.length;i++)
		{
			System.out.println("\n Round "+(i+1));
			System.out.println("\n Hello "+player_list[i].player_name);
			playGame(player_list[i],getWords());
		}
			

		System.out.println("\n");
/*
 * Print all the players along with their scores.
 */
		for (int i = 0;i<player_list.length;i++)
		{
			System.out.println("\n");
			System.out.println("PLayer no == "+player_list[i].player_number);
			System.out.println("PLayer name == "+player_list[i].player_name);
			System.out.println("PLayer score == "+player_list[i].player_score);
		}
/*
 * Sort the player_list array using selection sort and replace the original
 * array woth the sorted array.
 */
		player_list = sort_players(player_list);
/*
 * After sorting the array, we will now print the top 4 scoring players in
 * descending order.
 */
		System.out.println("\nThe Top (max upto 4) players are :\n");
		System.out.println("");

		for (int i = 0;i<player_list.length;i++) // Printing Top 4 players according to score
		{
			System.out.println("PLayer no == "+player_list[i].player_number);
			System.out.println("PLayer name == "+player_list[i].player_name);
			System.out.println("PLayer score == "+player_list[i].player_score);
		
			System.out.println("\n\n\n\n");

			if (i == 4)
				break;
		}

	}
}

