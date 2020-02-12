package com.syahputrareno975.dompetku.model.menu;

import android.content.Context;

import com.syahputrareno975.dompetku.R;
import com.syahputrareno975.dompetku.model.BaseModel;

import java.util.ArrayList;

public class MenuModel extends BaseModel {
    public int Id;
    public int Icon;
    public String Text;

    public MenuModel(int id, int icon, String text) {
        Id = id;
        Icon = icon;
        Text = text;
    }

    public static final int ID_FOOD_EXPENSE = 0;
    public static final int ID_TRANSPORT_EXPENSE = 1;
    public static final int ID_COMMON_NEED_EXPENSE = 2;
    public static final int ID_REPORT = 3;

    public static ArrayList<MenuModel> getMainMenuList(Context c) {
        ArrayList<MenuModel> menus = new ArrayList<>();
        menus.add(new MenuModel(ID_FOOD_EXPENSE, R.drawable.food,c.getString(R.string.food_expense)));
        menus.add(new MenuModel(ID_TRANSPORT_EXPENSE, R.drawable.bus,c.getString(R.string.transport_expense)));
        menus.add(new MenuModel(ID_COMMON_NEED_EXPENSE, R.drawable.commond_need,c.getString(R.string.common_expense)));
        menus.add(new MenuModel(ID_REPORT, R.drawable.report_menu,c.getString(R.string.menu_report)));
        return menus;
    }
}
