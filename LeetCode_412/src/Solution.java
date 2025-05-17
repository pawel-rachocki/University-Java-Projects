import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<String> fizzBuzz(int n) {
        List<String> answer = new ArrayList<String>(n);

        for (int i = 1; i <= n; i++) {
            //answer.add(Integer.toString(i));
            if (i % 15 == 0) {
                answer.add("FizzBuzz");
            } else if (i % 3 == 0) {
                answer.add("Fizz");
            } else if (i % 5 == 0) {
                answer.add("Buzz");
            } else {
                answer.add(Integer.toString(i));
            }
        }

        // for(String i : answer){
        //     System.out.print(i);
        // }

        return answer;

    }
}