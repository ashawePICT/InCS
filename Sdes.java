import java.lang.*;

// class to encrypt 1 block (1 byte) at a time 
class Encryption {

	int plaintext[]; // binary plaintext (8 bits)
	int key1[];		// binary key 1 (4 bits)
	int key2[];		// binary key 2 (4 bits)


	public Encryption(int[] plaintext, int[] key1, int[]key2)
	{
		this.plaintext = plaintext;
		this.key1 = key1;
		this.key2 = key2;
	}

}

// class to drcript
class Decryption {

}

class KeyGen {

	int[] key; // original 10 bit key

	public KeyGen(int[] key)
	{
		this.key = key;
	}

	int[][] genKeys(){
		int[] original = this.key;
		
		// generating key 1
		int[] afterp10 = p10(original);
		int[] part1 = new int[5];
		int[] part2 = new int[5];
		for(int i = 0; i < 10; i++)
		{
			if(i < 5)
				part1[i] = afterp10[i];
			else
				part2[i-5] = afterp10[i];
		}
		part1 = leftRoundShift(part1, 1);
		part2 = leftRoundShift(part2, 1);
		for(int i = 0; i < 10; i++)
		{
			if(i < 5)
				afterp10[i] = part1[i];
			else
				afterp10[i] = part2[i-5];
		}
		int[] afterp8 = p8(afterp10);

		int[] k1 = afterp8;	// 8 bit key 1
		Print.msg("Key 1 = ");
		Print.arr(k1);

		//generating key 2
		part1 = leftRoundShift(part1, 2);
		part2 = leftRoundShift(part2, 2);
		for(int i = 0; i < 10; i++)
		{
			if(i < 5)
				afterp10[i] = part1[i];
			else
				afterp10[i] = part2[i-5];
		}
		afterp8 = p8(afterp10);
		int[] k2 = afterp8;	// 8 bit key 2
		Print.msg("Key 2 = ");
		Print.arr(k2);
		int[][] keys = {k1,k2};
		return keys;
	}

	int[] leftRoundShift(int[] arr, int amount)
	{
		int len = arr.length;
		int[] temp = new int[amount];
		for(int i = 0; i < amount; i++)
			temp[i] = arr[i];

		for(int i = 0; i < len-amount; i++)
			arr[i] = arr[i+amount];

		for(int i = 0; i < amount; i++)
			arr[i+len-amount] = temp[i];
		
		return arr;
	}

	// exchange bits acc. to p10 table
	int[] p10(int[] key)
	{
		Print.msg("Before p10");
		Print.arr(key);
		// 3 5 2 7 4 10 1 9 8 6
		int[] output = new int[10];
		output[0]=key[2];
		output[1]=key[4];
		output[2]=key[1];
		output[3]=key[6];
		output[4]=key[3];
		output[5]=key[9];
		output[6]=key[0];
		output[7]=key[8];
		output[8]=key[7];
		output[9]=key[5];
		Print.msg("After p10");
		Print.arr(output);
		return output;
	}

	// exchange bits acc. to p8 table
	int[] p8(int[] key)
	{
		Print.msg("Before p8");
		Print.arr(key);
		// 6 3 7 4 8 5 10 9
		int[] output = new int[8];
		output[0]=key[5];
		output[1]=key[2];
		output[2]=key[6];
		output[3]=key[3];
		output[4]=key[7];
		output[5]=key[4];
		output[6]=key[9];
		output[7]=key[8];
		Print.msg("After p8");
		Print.arr(output);
		return output;
	}
}

public class Sdes {

	public void stringInputEncryption(String plaintext, String key)
	{
		try{
			System.out.println("Plaintext = " + plaintext);
			System.out.println("Key = " + key);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("here");
			printHelp();
		}
	}

	public void stringInputDecryption(String plaintext, String key)
	{
		try{
			System.out.println("Plaintext = " + plaintext);
			System.out.println("Key = " + key);
		}
		catch(ArrayIndexOutOfBoundsException e)
		{
			System.out.println("here2");
			printHelp();
		}
	}

	public void stringInput(String[] args, int i)
	{
		// System.out.println("args[i]" + args[i]);
		if(args[i].equals("enc"))
		{
			System.out.println("Encrypting File");
			stringInputEncryption(args[i+1],args[i+2]);
		}
		else if(args[i].equals("dec"))
		{
			System.out.println("Decrypting File");
			stringInputDecryption(args[i+1],args[i+2]);
		}
		else
		{
			System.out.println("here1");
			printHelp();
		}
	}

	public static void main(String[] args)
	{

		int[] key = {1,0,1,0,0,0,0,0,1,0};
		KeyGen kg = new KeyGen(key);
		int[][] keys = kg.genKeys();
		System.out.println("key1");
		for(int i = 0; i < 8; i++)
		{
			System.out.print(keys[0][i]);
		}
		System.out.println("\nkey2");
		for(int i = 0; i < 8; i++)
		{
			System.out.print(keys[1][i]);
		}

		// Sdes implementation = new Sdes();

		// if(args.length == 0) implementation.printHelp();
		// else
		// {
		// 	// System.out.println("here12 args[0].charAt(0)=" + args[0].charAt(0));
		// 	if(args[0].charAt(0) == '-')
		// 	{
		// 		// System.out.println("here123");
		// 		// user wants to choose between file and string
		// 		if(args[0].charAt(1) == 's')
		// 		{
		// 			// System.out.println("here1513");
		// 			System.out.println("String input option selected");
		// 			implementation.stringInput(args,1);
		// 		}
		// 	}
		// 	else
		// 	{
		// 		implementation.stringInput(args,0);
		// 	}
		// }
	}

	public void printHelp()
	{
		System.out.println("SDES - Implementation of Simple DES algorithm.\n\nUSAGE:\njava Sdes [-s/-f] enc/dec \"plaintext or file path\" 1010100110 \n\nExplaination:\narg1 (optional) - default: (-s)string; -f => file input; -s => string input; ex: java sdes -s enc \"text\" 1010101010 or java sdes -f \"text.txt\" 1010101010\narg2 - to enc type 'enc' else 'dec'\narg3 - plaintext / file to encrypt / decrypt\narg4 - 10 bit key in binary");
	}
}

class Print{

	static void arr(int[] a)
	{
		for(int i = 0; i < a.length; i++)
		{
			System.out.print(a[i]);
		}
		System.out.println();
	}
	
	static void msg(String text)
	{
		System.out.println(text);
	}
}