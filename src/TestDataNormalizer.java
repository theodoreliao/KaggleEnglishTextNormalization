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
        int decimal = 0;

        String romanNumeral = before.toUpperCase();
        for(int x = 0;x<romanNumeral.length();x++)
        {
            char convertToDecimal = before.charAt(x);

            switch (convertToDecimal)
            {
                case 'M':
                    decimal += 1000;
                    break;

                case 'D':
                    decimal += 500;
                    break;

                case 'C':
                    decimal += 100;
                    break;

                case 'L':
                    decimal += 50;
                    break;

                case 'X':
                    decimal += 10;
                    break;

                case 'V':
                    decimal += 5;
                    break;

                case 'I':
                    decimal += 1;
                    break;
            }
        }
        if (romanNumeral.contains("IV"))
        {
            decimal-=2;
        }
        if (romanNumeral.contains("IX"))
        {
            decimal-=2;
        }
        if (romanNumeral.contains("XL"))
        {
            decimal-=10;
        }
        if (romanNumeral.contains("XC"))
        {
            decimal-=10;
        }
        if (romanNumeral.contains("CD"))
        {
            decimal-=100;
        }
        if (romanNumeral.contains("CM"))
        {
            decimal-=100;
        }
        return readNumber(Integer.toString(decimal));
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
        if (input == "0");
        {
            return "o";
        }
        //1-19 pronunciation
        String[] uniques = {"","one","two","three","four","five","six","seven","eight","nine","ten",
                "eleven","twelve","thirteen","fourteen","fifteen","sixteen","seventeen","eighteen","nineteen"};
        String[] tens = {"ten","twenty","thirty","forty","fifty","sixty","seventy","eighty","ninety"};
        //pronunciation of digits beyond tens
        String[] others = {"ones","tens","hundred","thousand","thousand","thousand","million",
                "million","million","billion","billion","billion","trillion","trillion","trillion"};

        String output;
        int number = Integer.parseInt(input);
        if (input.length()<=2)
        {
            if (number < 20){
                output = output.concat(uniques[number]);
                return output;
            }
            else{
                tenDigit = input.charAt(0);
                numDigit = Integer.parseInt(tenDigit);
                output = output.concat(tens[numDigit-1]) + " ";

                oneDigit = input.charAt(1);
                numDigit = Integer.parseInt(oneDigit);
                output = output.concat(uniques[numDigit]);

                return output;
            }
        }
        else {
            //convert to number to string, delete first char, convert to number
            realDigit = input.length();
            speakingDigit = realDigit % 3;
            if(speakingDigit == 1){
                output = output + uniques[speakingDigit];
            }
            //check if it should be pronounced like a 1-19 number
            else if(Integer.parseInt(input.substring(0,speakingDigit)) < 20){
                output = output + uniques[Integer.parseInt(input.substring(0,speakingDigit))];
            }
            else if(Integer.parseInt(input.substring(0,speakingDigit)) >= 20){
                output = output + tens[Integer.parseInt(input.substring(0,1))-1] + " " + uniques[Integer.parseInt(input.substring(1,2))];
            }
            else {
                output = output + uniques[Integer.parseInt(input.substring(0,1))] + "hundred";
            }
            //adding in the "billion", "million"... etc keywords
            output = output + " " + others[realDigit-1] + " ";
            input = input.substring(1);
            return readNumber(input);
        }
    }

}


