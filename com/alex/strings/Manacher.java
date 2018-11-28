package com.alex.strings;


public class Manacher	{
	int N;	// size of the modified string

	/**
	 * longest[i] = If i is odd, it's the largest even palindrome centered at position i / 2.
	 *				Otherwise, it's the size of the largest odd palindrome centered at position i / 2.
	**/
	int [] longest;
	
	// len of the longest palindromic substring
	int longestPalindromeLen;
	
	// number of palindromic substrings
	int countPalindromicSubstrings;
	
	//
	public Manacher(String str)	{
		
		N = 1 + (str.length() << 1);	// N is the length of the modified string
		
		// modify strings ( abcba = -a-b-c-b-a- )
		int [] s = new int [ N ];
		for (int i = 0, k = 1; i < str.length(); i++, k += 2)	{
			s[k] = str.charAt(i);
		}
		
		//
		
		longest = new int [N];
		
		int center = 0, last = 0; // (center,last) represents the palindrome centered at center that ends at last, we store the value with the greatest value of last here
		
		for	(int i = 1;	i < N;	i++) //we start at 1 because there are no non-trivial palindromes with center at the first character
		{
		    if(last > i)
		        longest[i] = Math.min( longest[center-(i-center)], last-i );
		    /*
		        Try to prove that this if is correct by yourself. See that j = center-(i-center) is the symmetric of i with center as the point of symmetry. Then the longest palindrome centered at j that lies between the limits of the palindrome centered at center, is also a palindrome centered at i. I know this is quite confusing, so try proving it yourself.
		    */
		    while( i + longest[i] + 1 < N	&&	i - longest[i] - 1 >= 0	&&	s[ i+longest[i]+1 ] == s[ i-longest[i]-1 ]	)
		        longest[i]++;
		    // We try to make the palindrome as long as possible.
		    if(i+longest[i] > last)
		    {
		        last = i+longest[i];
		        center = i;
		    }
		    // we update (center,last)
		}
		
		//
		
		longestPalindromeLen = 0;
		countPalindromicSubstrings = 0;
		for	(int i = 0;	i < N;	i++)	{
			
			countPalindromicSubstrings += longest[i] >> 1;
			
			longest[i] = 2 * longest[i] + 1; // this is because longest stored only the length of half of the palindrome
			longestPalindromeLen = Math.max(longestPalindromeLen, longest[i]);
		}

		// palindromic substrings of size > 1 are counted
		countPalindromicSubstrings += str.length();	// count palindromic substring of size == 1
		
		
	}
}
