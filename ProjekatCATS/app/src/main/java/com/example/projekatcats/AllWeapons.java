package com.example.projekatcats;

import android.content.Context;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AllWeapons {
    public  static List<Weapon> weapons;

    public AllWeapons(Context context) {
        weapons = new ArrayList<>(Arrays.asList(
                new Weapon(context,"Stinger", 50, 20, 3, R.drawable.stinger, 50, 70, 1),
                new Weapon(context,"Saw", 70, 30, 4, R.drawable.saw, 50, 70, 2),
                new Weapon(context,"Rocket", 100, 30, 5, R.drawable.rocket, 75, 75, 3),
                new Weapon(context,"Forklift", 70, 40, 5, R.drawable.forklift, 174, 18, 4),
                new Weapon(context,"Blade", 150, 40, 5, R.drawable.blade, 94, 37, 5),

                new Weapon(context,"Wheele", 0, 100, 4, R.drawable.wheel1, 75, 75, 6),
                new Weapon(context,"Wheelo", 0, 120, 5, R.drawable.wheel2, 80, 80, 7)));
    }

}
