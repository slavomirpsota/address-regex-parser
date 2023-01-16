package com.example.demo.util;

public class RegexUtil {
    public static final String STREET_HOUSENO_MUNICIPALITY_REGEX = 	"[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+ (\\d+\\/?\\d?)[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ ]{0,20}( \\d+)?(\\,)?[,\\s][\\.a-záäčďéíľňóôšťúž.A-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔ.\\s]+(\\.)?(?!\\d)";
    public static final String STREET_HOUSENO_ZIP_MUNICIPALITY = "[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+ (\\d+\\/?\\d?)+(\\,)? \\d{3} ?\\d{2}[,\\s][\\.a-záäčďéíľňóôšťúž.A-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔ.\\s]+(\\.)?";
    public static final String ZIP_MUNICIPALITY_STREET_HOUSENO = "\\d{3} ?\\d{2}(,\\s)?[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+(,)?(\\s)?[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+ (\\d+\\/?\\d?)+";
    public static final String MUNICIPALITY_HOUSENO = "[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+(,\\s)?[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+ (\\d+\\/?\\d?)+";
    public static final String FIRSTNAME_LASTNAME_WITHOUT_TITLES_REGEX = "(\\p{L}+)\\s? (\\p{L}+)(?!([a-zA-Zs]+\\.))";
    public static final String ACADEMIC_TITLES = "[a-zA-Z]+\\.[a-zA-Z]?\\.?[a-zA-Z]?\\.?";
    public static final String ACADEMIC_TITLE_WITH_WHITESPACE = "[a-zA-Z\\s]+[a-zA-Z]\\.";
    public static final String STREET_REGEX = "[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s]+(?=\\d+)";
    public static final String HOUSE_NO_REGEX = "(?<=[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+ )((\\d+)\\/?(\\d+)?)[a-zA-Z\\s]{0,5}";
    public static final String MUNICIPALITY_REGEX = "(?<=\\d?\\s{0,10}?,?\\s{0,10})[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+";
    public static final String ZIP_REGEX = "\\d{3}\\s{0,1}\\d{2}";

}
