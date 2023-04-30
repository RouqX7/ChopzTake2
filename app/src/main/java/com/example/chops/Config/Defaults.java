package com.example.chops.Config;

import com.example.chops.Domain.CategoryDomain;
import com.example.chops.R;

import java.util.HashMap;
import java.util.Map;

public class Defaults {
    public static CategoryDomain[] defaultFoodCatagories = {
      new CategoryDomain("all","cat_4"),
      new CategoryDomain("Pizza","cat_1"),
      new CategoryDomain("Burgers", "cat_2"),
      new CategoryDomain("Chinese","cat_3"),
      new CategoryDomain("Chippers","cat_4")
    };
    public  static Map<String, Integer> defaultResturantImages;
    static {
        defaultResturantImages = new HashMap<String, Integer>();
        defaultResturantImages.put("rest1ImageBtn", R.id.rest1ImageBtn);
        defaultResturantImages.put("rest2ImageBtn", R.id.rest2ImageBtn);
        defaultResturantImages.put("rest3ImageBtn", R.id.rest3ImageBtn);
        defaultResturantImages.put("rest4ImageBtn", R.id.rest4ImageBtn);
        defaultResturantImages.put("rest5ImageBtn", R.id.rest5ImageBtn);
        defaultResturantImages.put("rest6ImageBtn", R.id.rest6ImageBtn);
        defaultResturantImages.put("rest7ImageBtn", R.id.rest7ImageBtn);
        defaultResturantImages.put("rest8ImageBtn", R.id.rest8ImageBtn);
    }
    public  static Map<String, Integer> defaultResturantImageTexts;
    static {
        defaultResturantImageTexts = new HashMap<String, Integer>();
        defaultResturantImageTexts.put("rest1ImageBtn", R.id.rest1text);
        defaultResturantImageTexts.put("rest2ImageBtn", R.id.rest2text);
        defaultResturantImageTexts.put("rest3ImageBtn", R.id.rest3text);
        defaultResturantImageTexts.put("rest4ImageBtn", R.id.rest5text);
        defaultResturantImageTexts.put("rest5ImageBtn", R.id.rest6text);
        defaultResturantImageTexts.put("rest6ImageBtn", R.id.rest7text);
        defaultResturantImageTexts.put("rest7ImageBtn", R.id.rest8text);
        defaultResturantImageTexts.put("rest8ImageBtn", R.id.rest9text);
    }
    public  static Map<String, Integer> defaultFoodImages;
    static {
        defaultFoodImages = new HashMap<String, Integer>();
        defaultFoodImages.put("food1ImageBtn", R.id.food1ImageBtn);
        defaultFoodImages.put("food2ImageBtn", R.id.food2ImageBtn);
        defaultFoodImages.put("food3ImageBtn", R.id.food3ImageBtn);
        defaultFoodImages.put("food4ImageBtn", R.id.food4ImageBtn);
        defaultFoodImages.put("food5ImageBtn", R.id.food5ImageBtn);
        defaultFoodImages.put("food6ImageBtn", R.id.food6ImageBtn);
        defaultFoodImages.put("food7ImageBtn", R.id.food7ImageBtn);
        defaultFoodImages.put("food8ImageBtn", R.id.food8ImageBtn);
    }
    public  static Map<String, Integer> defaultFoodImageTexts;
    static {
        defaultFoodImageTexts = new HashMap<String, Integer>();
        defaultFoodImageTexts.put("food1ImageBtn", R.id.food1text);
        defaultFoodImageTexts.put("food2ImageBtn", R.id.food2text);
        defaultFoodImageTexts.put("food3ImageBtn", R.id.food3text);
        defaultFoodImageTexts.put("food4ImageBtn", R.id.food5text);
        defaultFoodImageTexts.put("food5ImageBtn", R.id.food6text);
        defaultFoodImageTexts.put("food6ImageBtn", R.id.food7text);
        defaultFoodImageTexts.put("food7ImageBtn", R.id.food8text);
        defaultFoodImageTexts.put("food8ImageBtn", R.id.food9text);
    }
}
