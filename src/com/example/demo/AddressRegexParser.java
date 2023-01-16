package com.example.demo;

import com.example.demo.dto.AddressDto;
import com.example.demo.util.RegexUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddressRegexParser {

    public static void main(String[] args) {


        List<String> inputNames = Arrays.asList("Mgr. Jozef      Mrkvička", "Mgr. Ignac Pukalovic PhDr.", "Lojzo Odborny RnDr.", "Pan Puklicka", "Janko Hrasko Ing.",
                "PhDr. Ing. Dr.h.c. PharmDr. Mgr. Pista Lopatovic PhDr.");
        List<String> addresses = Arrays.asList("Ulica Svornosti 39, Bratislava", "Lopatova 32, 821 01 Bratislava", "08001, Prešov, Poštová 38",
                "Spišská Nová Ves 233");
        inputNames.forEach(c -> {

            String fullName = parseFullName(c.trim().replaceAll(" +", " "));
            System.out.println("input: " + c);
            System.out.println("cele meno: " + fullName);
            System.out.println("meno: " + parseFirstNameFromFullName(fullName));
            System.out.println("priezvisko: " + parseLastNameFromFullName(fullName));
            System.out.println("tituly: " + parseTitles(c));
            System.out.println("\n");
        });

        addresses.forEach(c -> {
            AddressDto address = parseAddress(c);
            System.out.println("input: " + c);
            System.out.println("address obj: " + address);
            System.out.println("/n");
            checkMappedDataAndLogMissingToFile(address, 1);
        });


    }

    public static String parseFullName(String in) {
        Matcher matcher = Pattern.compile(RegexUtil.FIRSTNAME_LASTNAME_WITHOUT_TITLES_REGEX).matcher(in);
        if (matcher.find()) {
            return matcher.group();
        }
        return "";
    }

    public static String parseFirstNameFromFullName(String in) {
        String[] out = in.trim().split("\\s+");
        if (out.length < 2 || out.length > 3) {
            throw new IllegalArgumentException("Cannot surely define first name from input String: " + in);
        }
        return out[0];
    }

    public static String parseLastNameFromFullName(String in) {
        String[] out = in.trim().split("\\s+");
        if (out.length < 2 || out.length > 3) {
            throw new IllegalArgumentException("Cannot surely define first name from input String: " + in);
        }
        return out[1];
    }

    public static List<String> parseTitles(String in) {
        Matcher matcher = Pattern.compile(RegexUtil.ACADEMIC_TITLES).matcher(in);
        List<String> out = new ArrayList<>();
        while (matcher.find()) {
            String group = matcher.group();
            out.add(group);
        }
        return out;
    }

    public static AddressDto parseAddress(String in) {
        Matcher street_houseno_municipality_matcher = Pattern.compile(RegexUtil.STREET_HOUSENO_MUNICIPALITY_REGEX).matcher(in);
        Matcher street_houseno_zip_municipality_matcher = Pattern.compile(RegexUtil.STREET_HOUSENO_ZIP_MUNICIPALITY).matcher(in);
        Matcher zip_municipality_street_houseno_matcher = Pattern.compile(RegexUtil.ZIP_MUNICIPALITY_STREET_HOUSENO).matcher(in);
        Matcher municipality_houseno_matcher = Pattern.compile(RegexUtil.MUNICIPALITY_HOUSENO).matcher(in);
        String addressString = "";

        if (street_houseno_municipality_matcher.find()) {
            addressString = street_houseno_municipality_matcher.group(0);
            return parseStreetHouseNoMunicipality(addressString);
        } else if (street_houseno_zip_municipality_matcher.find()) {
            addressString = street_houseno_zip_municipality_matcher.group();
            return parseStreetHouseNoZipMunicipality(addressString);
        } else if (zip_municipality_street_houseno_matcher.find()) {
            addressString = zip_municipality_street_houseno_matcher.group();
            return parseZipMunicipalityStreetHouseNo(addressString);
        } else if (municipality_houseno_matcher.find()) {
            addressString = municipality_houseno_matcher.group();
            return parseMunicipalityHouseNo(addressString);
        } else {
            System.out.println(String.format("Couldn't match any pattern for input address String: %s", in));
            return null;
        }
    }

    public static AddressDto parseStreetHouseNoMunicipality(String in) {
        AddressDto out = new AddressDto();
        List<String> delimitedAddresses = Arrays.asList(in.trim().split(","));
        delimitedAddresses = delimitedAddresses.stream().map(String::trim).collect(Collectors.toList());

        parseStreet(out, delimitedAddresses.get(0));
        parseHouseNo(out, delimitedAddresses.get(0));
        parseMunicipality(out, delimitedAddresses.get(1));

        return out;
    }

    public static AddressDto parseStreetHouseNoZipMunicipality(String in) {
        AddressDto out = new AddressDto();
        List<String> delimitedAddresses = Arrays.asList(in.trim().split(","));
        delimitedAddresses = delimitedAddresses.stream().map(String::trim).collect(Collectors.toList());

        parseStreet(out, delimitedAddresses.get(0));
        parseHouseNo(out, delimitedAddresses.get(0));
        parseZipCode(out, delimitedAddresses.get(1));
        parseMunicipality(out, delimitedAddresses.get(1));

        return out;
    }

    public static AddressDto parseZipMunicipalityStreetHouseNo(String in) {
        AddressDto out = new AddressDto();
        List<String> delimitedAddresses = Arrays.asList(in.trim().split(","));

        if (delimitedAddresses.size() > 2) {
            parseZipCode(out, delimitedAddresses.get(0));
            parseMunicipality(out, delimitedAddresses.get(1));
            parseStreet(out, delimitedAddresses.get(2));
            parseHouseNo(out, delimitedAddresses.get(2));
        } else if (delimitedAddresses.size() == 1) {
            parseZipCode(out, delimitedAddresses.get(0));
            parseMunicipality(out, delimitedAddresses.get(0));
            parseStreet(out,delimitedAddresses.get(0));
            parseHouseNo(out,delimitedAddresses.get(0));
        } else {
            parseZipCode(out, delimitedAddresses.get(0));
            parseMunicipality(out, delimitedAddresses.get(0));
            parseStreet(out, delimitedAddresses.get(1));
            parseHouseNo(out, delimitedAddresses.get(1));
        }

        return out;
    }

    public static AddressDto parseMunicipalityHouseNo(String in) {
        Matcher matcher = Pattern.compile("[a-záäčďéíľňóôšťúžýA-ZČÁÄĎÉÍĽŇÓŠŤÚŽÔÝ\\s.]+").matcher(in);
        AddressDto out = new AddressDto();
        if (matcher.find()) {
            out.setMunicipality(matcher.group());
            out.setStreet(matcher.group());
        }
        parseHouseNo(out, in);

        return out;
    }

    public static AddressDto parseStreet(AddressDto dto, String in) {
        Matcher matcher = Pattern.compile(RegexUtil.STREET_REGEX).matcher(in);
        if (matcher.find()) {
            String street = matcher.group().trim().replaceAll(" +", " ");
            dto.setStreet(street);
        }
        return dto;
    }

    public static AddressDto parseHouseNo(AddressDto dto, String in) {
        Matcher matcher = Pattern.compile(RegexUtil.HOUSE_NO_REGEX).matcher(in);
        if (matcher.find()) {
            String houseNo = matcher.group();
            List<String> delimittitedBySlash = Arrays.asList(houseNo.split("/"));

            if (delimittitedBySlash.size() > 1) {
                dto.setConscriptionNo(delimittitedBySlash.get(0).trim());
                dto.setOrientationalNo(delimittitedBySlash.get(1).trim());
            } else {
                dto.setOrientationalNo(delimittitedBySlash.get(0).trim());
            }
        }
        return dto;
    }

    public static AddressDto parseMunicipality(AddressDto dto, String in) {
        String inWithoutZip = in.replaceAll(RegexUtil.ZIP_REGEX, "");
        Matcher matcher = Pattern.compile(RegexUtil.MUNICIPALITY_REGEX).matcher(inWithoutZip);
        if (matcher.find()) {
            dto.setMunicipality(matcher.group().trim());
        }
        return dto;
    }

    public static AddressDto parseZipCode(AddressDto dto, String in) {
        Matcher matcher = Pattern.compile(RegexUtil.ZIP_REGEX).matcher(in);
        if (matcher.find()) {
            dto.setZip(matcher.group());
        }
        return dto;
    }


    public static void checkMappedDataAndLogMissingToFile(AddressDto dto, int rowNumber) {
        try {
            Objects.requireNonNull(dto);
            File outFile = new File("migration_output_err_log.txt");
            StringBuilder logString = new StringBuilder();
            BufferedWriter writer = new BufferedWriter(new FileWriter(outFile));
            logString.append("Error occurred on line: ").append(rowNumber);

            if (Objects.isNull(dto.getMunicipality()) || StringUtils.isBlank(dto.getMunicipality())) {
                logString.append(" ").append("error parsing 'Municipality'");
            }
            if (Objects.isNull(dto.getStreet()) || StringUtils.isBlank(dto.getStreet())) {
                logString.append(" ").append("error parsing 'Street'");;
            }
            if (Objects.isNull(dto.getConscriptionNo()) || StringUtils.isBlank(dto.getConscriptionNo())) {
                logString.append(" ").append("error parsing 'Conscription Number'");
            }
            if (Objects.isNull(dto.getOrientationalNo()) || StringUtils.isBlank(dto.getOrientationalNo())) {
                logString.append(" ").append("error parsing 'Orientational Number'");
            }
            if (Objects.isNull(dto.getZip()) || StringUtils.isBlank(dto.getZip())) {
                logString.append(" ").append("error parsing 'Zip'");
            }

            writer.write(logString + "\n");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
