import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class IceCreamParlorResult {

    /*
     * Complete the 'icecreamParlor' function below.
     *
     * The function is expected to return an INTEGER_ARRAY.
     * The function accepts following parameters:
     *  1. INTEGER m
     *  2. INTEGER_ARRAY arr
     */

    public static int icecreamParlor(int m, List<Integer> arr) {
        int[] n = new int[arr.size()];
        for(int i = 0; i < n.length; i++) n[i] = arr.get(i);


        //recursive:
        //return countSumsRec(n, 0, m, 2);

        //dp:
        return countSumsDp( n, m, 2);
    }

    /*
    Count the ways to combine arr such that `initial_f` items sum to `initial_t`
    */
    public static int countSumsDp(int[] arr, int initial_t, int initial_f) {
        int[][][] dp = new int[arr.length + 1][initial_t + 1][initial_f + 1];

        for(int t = 0; t < initial_t; t++) {
            for(int f = 0; f < initial_f; f++) {
                dp[arr.length][t][f] = 0;
            }
        }

        for(int i = 0; i < dp.length; i++) {
            for(int t = 0; t < dp[i].length; t++) {
                dp[i][t][0] = 0;
            }
            dp[i][0][0] = 1;
        }

        for(int i = dp.length - 2; i >= 0; i--) {
            for(int t = 1; t <= initial_t; t++) {
                for(int f = initial_f; f > 0; f--) {
                    if(t < arr[i]) {
                        dp[i][t][f] = dp[i + 1][t][f];
                    } else {
                        dp[i][t][f] = dp[i + 1][t - arr[i]][f - 1] + dp[i + 1][t][f];
                    }
                }
            }
        }

        return dp[0][initial_t][initial_f];
    }

    /*
    Count the ways to combine arr[i..] such that `f` items sum to `t` money.
    */
    public static int countSumsRec(int[] arr, int i, int t, int f) {
        System.out.println(Arrays.toString(arr) + " " + i + " " + t + " " + f);

        //if the friends and the money reach 0 at the same time, this is a working case
        if(t == 0 && f == 0) return 1;


        //if we've ran out of friends and still have money, this is not a working case
        if (f == 0) return 0;

        //if we get past the end of the array without working, this is not a working case
        if (i == arr.length) return 0;

        //if the current considered flavour is too expensive, don't take it
        if(t < arr[i]) return countSumsRec(arr, i + 1, t, f);

        //otherwise, combine the possibilities from taking and not-taking
        return countSumsRec(arr, i+1, t - arr[i], f - 1) + countSumsRec(arr, i + 1, t, f);
    }

}

public class IceCreamParlorSolution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        int t = Integer.parseInt(bufferedReader.readLine().trim());

        IntStream.range(0, t).forEach(tItr -> {
            try {
                int m = Integer.parseInt(bufferedReader.readLine().trim());

                int n = Integer.parseInt(bufferedReader.readLine().trim());

                List<Integer> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                    .map(Integer::parseInt)
                    .collect(toList());

                Integer result = IceCreamParlorResult.icecreamParlor(m, arr);

                bufferedWriter.write(
                    result + "\n"
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
