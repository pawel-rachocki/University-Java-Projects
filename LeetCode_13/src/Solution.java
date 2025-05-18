class Solution {
    private int convert(char c){
        switch(c){
            case 'I' : return 1;
            case 'V' : return 5;
            case 'X' : return 10;
            case 'L' : return 50;
            case 'C' : return 100;
            case 'D' : return 500;
            case 'M' : return 1000;
            default:   return 0;
        }
    }
    public int romanToInt(String s) {
        int resultCounter = 0;
        int curr = convert(s.charAt(0));

        for(int i = 1; i < s.length(); i++){
            int next = convert(s.charAt(i));
            if(curr < next){
                resultCounter -= curr;
            }else {
                resultCounter += curr;
            }
            curr = next;
        }

        resultCounter += curr;

        return resultCounter;

    }
}