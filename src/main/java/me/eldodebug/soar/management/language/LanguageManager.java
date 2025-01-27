package me.eldodebug.soar.management.language;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Slf4j
public class LanguageManager {

    private HashMap<String, String> translateMap = new HashMap<String, String>();

    private Language currentLanguage;

    public LanguageManager() {
        setCurrentLanguage(Language.ENGLISH);
    }

    private void loadMap(HashMap<String, String> map, String language) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(LanguageManager.class.getClassLoader().getResourceAsStream("assets/minecraft/soar/language/" + language + ".properties"), StandardCharsets.UTF_8))) {

            String s;

            while ((s = reader.readLine()) != null) {

                if (!s.equals("") && !s.startsWith("#")) {
                    String[] args = s.split("=");

                    map.put(args[0], args[1]);
                }
            }

        } catch (Exception e) {
            log.error("Failed to load translate", e);
        }
    }

    public Language getCurrentLanguage() {
        return currentLanguage;
    }

    public void setCurrentLanguage(Language currentLanguage) {

        this.currentLanguage = currentLanguage;
        this.loadMap(translateMap, currentLanguage.getId());

        for (TranslateText text : TranslateText.values()) {
            if (translateMap.containsKey(text.getKey())) {
                text.setText(translateMap.get(text.getKey()));
            }
        }
    }
}
