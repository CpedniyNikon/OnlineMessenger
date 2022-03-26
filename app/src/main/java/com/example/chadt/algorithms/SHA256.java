package com.example.chadt.algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class SHA256 {
    private final String text;
    private String cipherText;
    private final Map<Character, String> HtoB = Map.ofEntries(
            Map.entry('0', "0000"), Map.entry('1', "0001"), Map.entry('2', "0010"), Map.entry('3', "0011"),
            Map.entry('4', "0100"), Map.entry('5', "0101"), Map.entry('6', "0110"), Map.entry('7', "0111"),
            Map.entry('8', "1000"), Map.entry('9', "1001"), Map.entry('a', "1010"), Map.entry('b', "1011"),
            Map.entry('c', "1100"), Map.entry('d', "1101"), Map.entry('e', "1110"), Map.entry('f', "1111")
    );

    private final Map<String, Character> BtoH = Map.ofEntries(
            Map.entry("0000", '0'), Map.entry("0001", '1'), Map.entry("0010", '2'), Map.entry("0011", '3'),
            Map.entry("0100", '4'), Map.entry("0101", '5'), Map.entry("0110", '6'), Map.entry("0111", '7'),
            Map.entry("1000", '8'), Map.entry("1001", '9'), Map.entry("1010", 'A'), Map.entry("1011", 'B'),
            Map.entry("1100", 'C'), Map.entry("1101", 'D'), Map.entry("1110", 'E'), Map.entry("1111", 'F')
    );

    private String h0 = "0x6a09e667";
    private String h1 = "0xbb67ae85";
    private String h2 = "0x3c6ef372";
    private String h3 = "0xa54ff53a";
    private String h4 = "0x510e527f";
    private String h5 = "0x9b05688c";
    private String h6 = "0x1f83d9ab";
    private String h7 = "0x5be0cd19";

    ArrayList<String> k = new ArrayList<>(Arrays.asList(
            "0x428a2f98", "0x71374491", "0xb5c0fbcf", "0xe9b5dba5", "0x3956c25b", "0x59f111f1", "0x923f82a4", "0xab1c5ed5",
            "0xd807aa98", "0x12835b01", "0x243185be", "0x550c7dc3", "0x72be5d74", "0x80deb1fe", "0x9bdc06a7", "0xc19bf174",
            "0xe49b69c1", "0xefbe4786", "0x0fc19dc6", "0x240ca1cc", "0x2de92c6f", "0x4a7484aa", "0x5cb0a9dc", "0x76f988da",
            "0x983e5152", "0xa831c66d", "0xb00327c8", "0xbf597fc7", "0xc6e00bf3", "0xd5a79147", "0x06ca6351", "0x14292967",
            "0x27b70a85", "0x2e1b2138", "0x4d2c6dfc", "0x53380d13", "0x650a7354", "0x766a0abb", "0x81c2c92e", "0x92722c85",
            "0xa2bfe8a1", "0xa81a664b", "0xc24b8b70", "0xc76c51a3", "0xd192e819", "0xd6990624", "0xf40e3585", "0x106aa070",
            "0x19a4c116", "0x1e376c08", "0x2748774c", "0x34b0bcb5", "0x391c0cb3", "0x4ed8aa4a", "0x5b9cca4f", "0x682e6ff3",
            "0x748f82ee", "0x78a5636f", "0x84c87814", "0x8cc70208", "0x90befffa", "0xa4506ceb", "0xbef9a3f7", "0xc67178f2"
    ));

    private String rightRotate(String str, int rotate) {
        String res = str;
        String sub = res.substring(res.length() - rotate);
        res = (sub + res);
        res = res.substring(0, res.length() - rotate);
        return res;
    }

    private String rightShift(String str, int shift) {
        String res = str;
        StringBuilder sub = new StringBuilder(new String(new char[shift]));
        for (int i = 0; i < shift; i++) {
            sub.setCharAt(i, '0');
        }
        res = sub + res;
        res = res.substring(0, res.length() - shift);
        return res;
    }

    private String binaryBased(char c) {
        StringBuilder binary = new StringBuilder(new String(new char[8]));
        for (int i = 0; i < 8; i++) {
            binary.setCharAt(i, '0');
        }
        int c_ = (int) c;
        int index = 7;
        while (c_ > 0) {
            binary.setCharAt(index, (char) ((c_ % 2) + '0'));
            c_ /= 2;
            index--;
        }
        return binary.toString();
    }

    private String endAppend(int length) {
        StringBuilder result = new StringBuilder(new String(new char[64]));
        for (int i = 0; i < 64; i++) {
            result.setCharAt(i, '0');
        }
        int index = 63;
        while (length > 0) {
            result.setCharAt(index, (char) (length % 2 + '0'));
            length /= 2;
            index--;
        }
        return result.toString();
    }

    private String xorString(String a, String b) {
        StringBuilder result = new StringBuilder(new String(new char[a.length()]));
        for (int i = 0; i < result.length(); i++) {
            result.setCharAt(i, '0');
        }
        for (int i = 0; i < a.length(); i++) {
            int cnt = 0;
            if (a.charAt(i) == '1') {
                cnt++;
            }
            if (b.charAt(i) == '1') {
                cnt++;
            }
            if (cnt == 1) {
                result.setCharAt(i, '1');
            } else {
                result.setCharAt(i, '0');
            }
        }
        return result.toString();
    }

    private String fromHexToBinary(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 2; i < str.length(); i++) {
            result.append(HtoB.get(str.charAt(i)));
        }
        return result.toString();
    }

    private String sumBinary(String a, String b) {
        StringBuilder result = new StringBuilder(new String(new char[a.length()]));
        for (int i = 0; i < a.length(); i++) {
            result.setCharAt(i, '0');
        }
        for (int i = a.length() - 1; i >= 0; i--) {
            int value = (result.charAt(i) - '0') + (a.charAt(i) - '0') + (b.charAt(i) - '0');
            if (value >= 2) {
                if (i > 0) {
                    result.setCharAt(i - 1, (char) ((result.charAt(i - 1)) + 1));
                }
                result.setCharAt(i, (char) (value % 2 + '0'));
            } else {
                result.setCharAt(i, (char) (value + '0'));
            }
        }
        return result.toString();
    }

    String multiplyStrings(String a, String b) {
        StringBuilder result = new StringBuilder(new String(new char[a.length()]));
        for (int i = 0; i < a.length(); i++) {
            result.setCharAt(i, '0');
        }
        for (int i = 0; i < a.length(); i++) {
            result.setCharAt(i, (char)(((a.charAt(i)-'0') * (b.charAt(i)-'0') + '0')));
        }
        return String.valueOf(result);
    }

    String notString(String a) {
        StringBuilder result = new StringBuilder(new String(new char[a.length()]));
        for (int i = 0; i < a.length(); i++) {
            result.setCharAt(i, '0');
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) == '0') {
                result.setCharAt(i, '1');
            } else {
                result.setCharAt(i, '0');
            }
        }
        return result.toString();
    }

    public SHA256(String text) {
        this.text = text;
    }

    public String getCipherText() {
        return cipherText;
    }

    public void encryptText() {


        StringBuilder hashes = new StringBuilder("");
        for (int i = 0; i < text.length(); i++) {
            hashes.append(binaryBased(text.charAt(i)));
        }
        int length = hashes.length();
        hashes.append("1");
        while ((hashes.length() + 64) % 512 != 0) {
            hashes.append("0");
        }
        hashes.append(endAppend(length));
        for (int i = 0; i < 48; i++) {
            hashes.append("00000000000000000000000000000000");
        }
        ArrayList<String> table = new ArrayList<>();
        String str = "";
        for (int i = 0; i < hashes.length(); i++) {
            str = str + hashes.charAt(i);
            if ((i + 1) % 32 == 0) {
                table.add(str);
                str = "";
            }
        }

        for (int i = 16; i < table.size(); i++) {
            String s0 = xorString(xorString(rightRotate(table.get(i - 15), 7), rightRotate(table.get(i - 15), 18)), rightShift(table.get(i - 15), 3));
            String s1 = xorString(xorString(rightRotate(table.get(i - 2), 17), rightRotate(table.get(i - 2), 19)), rightShift(table.get(i - 2), 10));
            String sum = sumBinary(sumBinary(sumBinary(table.get(i - 16), s0), table.get(i - 7)), s1);
            table.set(i, sum);
        }



        String a = fromHexToBinary(h0);
        String b = fromHexToBinary(h1);
        String c = fromHexToBinary(h2);
        String d = fromHexToBinary(h3);
        String e = fromHexToBinary(h4);
        String f = fromHexToBinary(h5);
        String g = fromHexToBinary(h6);
        String h = fromHexToBinary(h7);



        for (int i = 0; i < table.size(); i++) {
            String S1 = xorString(xorString(rightRotate(e, 6), rightRotate(e, 11)), rightRotate(e, 25));
            String ch = xorString(multiplyStrings(e, f), multiplyStrings(notString(e), g));
            String temp1 = sumBinary(sumBinary(sumBinary(sumBinary(h, S1), ch), fromHexToBinary(k.get(i))), table.get(i));
            String S0 = xorString(xorString(rightRotate(a, 2), rightRotate(a, 13)), rightRotate(a, 22));
            String maj = xorString(xorString(multiplyStrings(a, b), multiplyStrings(a, c)), multiplyStrings(b, c));
            String temp2 = sumBinary(S0, maj);
            h = g;
            g = f;
            f = e;
            e = sumBinary(d, temp1);
            d = c;
            c = b;
            b = a;

            a = sumBinary(temp1, temp2);
        }

        h0 = fromHexToBinary(h0);
        h1 = fromHexToBinary(h1);
        h2 = fromHexToBinary(h2);
        h3 = fromHexToBinary(h3);
        h4 = fromHexToBinary(h4);
        h5 = fromHexToBinary(h5);
        h6 = fromHexToBinary(h6);
        h7 = fromHexToBinary(h7);

        h0 = sumBinary(h0, a);
        h1 = sumBinary(h1, b);
        h2 = sumBinary(h2, c);
        h3 = sumBinary(h3, d);
        h4 = sumBinary(h4, e);
        h5 = sumBinary(h5, f);
        h6 = sumBinary(h6, g);
        h7 = sumBinary(h7, h);

        String BinaryDigest = h0 + h1 + h2 + h3 + h4 + h5 + h6 + h7;
        StringBuilder HexDigest = new StringBuilder();
        StringBuilder binarySubDigest = new StringBuilder();
        for (int i = 0; i < BinaryDigest.length(); i++) {
            binarySubDigest.append(BinaryDigest.charAt(i));
            if (binarySubDigest.length() == 4) {
                HexDigest.append(BtoH.get(binarySubDigest.toString()));
                binarySubDigest.delete(0, binarySubDigest.length());
            }
        }
        cipherText = HexDigest.toString();
    }


}
