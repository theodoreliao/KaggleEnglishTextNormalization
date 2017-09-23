public class TestDataNormalizer {

    public static void main(String[] args){
    }

    public static String identifyAndNormalize(String before){
        before = before.trim();
        if(before.matches("\\p{Punct}*")){
            return before;
        }

        if(before.matches("[a-z]*") || before.matches("[A-Z][a-z]*")){
            return before;
        }

        if(before.matches("[A-Z]*")){
            if(before.matches("^M{0,4}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$")){
                return readRomanNumeral(before);
            }
            else{
                return spreadAcronym(before);
            }
        }

        if(before.matches("[A-Z]*[s]]")){
            return spreadAcronym(before.substring(0, before.length()-2)) + "'s";
        }

        if(before.matches("[0-9]*")){
            if(before.length() == 4){
                return readYear(before);
            }
            else{
                return readNumber(before);
            }
        }

        if(before.matches("[0-9,]*")){
            //remove commas
            return readNumber(before.replaceAll(",", ""));
        }

        return null;
    }

    private static String readYear(String before) {
        int num = Integer.parseInt(before);
        if(num >= 2000 && num < 2010){
            return readNumber(before);
        }
        else{
            return readNumber(before.substring(0, 2)) + readNumber(before.substring(2));
        }
    }

    private static String readRomanNumeral(String before) {
        return "";
    }

    public static String spreadAcronym(String input){
        String output = "";
        input = input.toLowerCase();
        for(char c: input.toCharArray()){
            output += c + " ";
        }
        return output.trim();
    }

    public static String readNumber(String input){
        return "";
    }

}
