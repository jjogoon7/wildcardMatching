package com.company;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main (String[] args) throws java.lang.Exception
    {
//        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//        String input = br.readLine();
//        int cnt = Integer.parseInt(input);

        String[] lines = {
                "coding *ing",
                "interview in*",
                "apple app?e",
                "vanilla vani?a",
                "banana ba*na",
                "fruits fru*its",
                "Love Love*",
                "Landvibe L*d?i*e"};
        int cnt = lines.length;
        for(int i = 0; i < cnt; ++i) {
            String[] line = lines[i].split(" ");
            System.out.println(exaustiveSearch(line[0], line[1]) ? "True" : "False");
        }
    }

    public static boolean solution(String str, String pattern)
    {
        int n = str.length(), m = pattern.length();
        if(m == 0) return (n == 0);

        boolean[][] lookup = new boolean[n + 1][m + 1];
        for (int i = 0; i < n + 1; ++i) Arrays.fill(lookup[i], false);

        lookup[0][0] = true;

        for (int i = 1; i <= m; ++i)
        {
            if (pattern.charAt(i-1) == '*') lookup[0][i] = lookup[0][i-1];
        }

        for (int i = 1; i <= n; ++i)
        {
            for (int j = 1; j <= m; ++j)
            {
                if (pattern.charAt(j-1) == '*')
                {
                    lookup[i][j] = lookup[i][j-1] || lookup[i-1][j];
                }
                else if (pattern.charAt(j-1) == '?' || str.charAt(i-1) == pattern.charAt(j-1))
                {
                    lookup[i][j] = lookup[i-1][j-1];
                }
                else
                {
                    lookup[i][j] = false;
                }
            }
        }

        return lookup[n][m];
    }

    public static boolean solution2(String str, String pattern) {
        int m = str.length(), n = pattern.length();
        boolean[][] lookup = new boolean[m + 1][n + 1];
        for(int i = 0; i < m; ++i) Arrays.fill(lookup[i], false);
        lookup[0][0] = true;

        for(int i = 1; i <= n; ++i)
            if(pattern.charAt(i-1) == '*') lookup[0][i] = lookup[0][i-1];

        for(int i = 1; i <= m; ++i) {
            for(int j = 1; j <= n; ++j) {
                if(pattern.charAt(j-1) == '?' || pattern.charAt(j-1) == str.charAt(i-1))
                    lookup[i][j] = lookup[i-1][j-1];
                if(pattern.charAt(j-1) == '*')
                    lookup[i][j] = true;
            }
        }
        return lookup[m][n];
    }

    public static boolean solution3(String str, String pattern) {
        int s = 0, p = 0, q = 0;
        boolean match = true;
        while(match && p < pattern.length()) {
            if(s >= str.length()) break;
            if(pattern.charAt(p) == '*') {
                q = p + 1;
                match = true;
                s++;
                if(str.charAt(s) == pattern.charAt(q)) p++;
            } else if(pattern.charAt(p) == '?') {
                match = true;
                s++;
                p++;
            } else {
                match = str.charAt(s) == pattern.charAt(p);
                s++;
                p++;
            }
        }
        if(s < str.length() && q < pattern.length()) return str.charAt(s) == pattern.charAt(q);
        else if(s < str.length() && p < pattern.length()) return str.charAt(s) == pattern.charAt(p);

        return match && str.charAt(s) == pattern.charAt(p);
    }

    public static boolean exaustiveSearch(String str, String pattern) {
        int m = str.length(), n = pattern.length();
        int p = 0;

        // 첫 번째 조건
        while (p < n && p < m &&
                (pattern.charAt(p) == '?' || pattern.charAt(p) == str.charAt(p)))
            p++;

        // 두 번째 조건
        if(n == p)
            return p == m;

        // 세/네 번째 조건
        if(pattern.charAt(p) == '*') {
            int s = 0;
            while(s + p <= m) {
                if(exaustiveSearch(str.substring(s+p), pattern.substring(p+1)))
                    return true;
                s++;
            }
        }
        return false;
    }
}
