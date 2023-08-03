package com.epam.rd.autocode.floodfill;

import com.google.common.primitives.Chars;

import java.util.StringTokenizer;

public interface FloodFill {
    void flood(final String map, final FloodLogger logger);

    static FloodFill getInstance() {
        return new FloodFill() {
            @Override
            public void flood(String map, FloodLogger logger) {
                logger.log(map);

                if (map.indexOf(LAND) < 0) {
                    return;
                }

                char[] chars = map.toCharArray();
                StringTokenizer tokenizer = new StringTokenizer(map, "\n");
                String[] tokensArray = new String[tokenizer.countTokens()];
                int n = 0;
                while (tokenizer.hasMoreTokens()) {
                    String str = tokenizer.nextToken();
                    tokensArray[n++] = str;
                }

                for (int j = 0; j < tokensArray.length; j++) {
                    String str = tokensArray[j];
                    int length = str.length();
                    int line = length + 1;
                    for (int i = 0; i < length; i++) {
                        if (str.charAt(i) == WATER && Chars.contains(chars, LAND)) {
                            int ch = (length * j) + i + j;
                            int left = ch - 1;
                            int right = ch + 1;
                            int top = ch - line;
                            int down = ch + line;

                            if ((left - j * line) >= 0) {
                                chars[left] = WATER;
                            }
                            if ((right - j * line) < line - 1) {
                                chars[right] = WATER;
                            }
                            if (top >= 0) {
                                chars[top] = WATER;
                            }
                            if (down < map.length()) {
                                chars[down] = WATER;
                            }
                        }
                    }
                }
                flood(String.valueOf(chars), logger);
            }
        };
    }

    char LAND = '█';
    char WATER = '░';
}