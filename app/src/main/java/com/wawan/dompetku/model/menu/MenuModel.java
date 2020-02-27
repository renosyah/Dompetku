package com.wawan.dompetku.model.menu;

import android.content.Context;

import com.wawan.dompetku.R;
import com.wawan.dompetku.model.BaseModel;

import java.util.ArrayList;

// adalah model menu yg digunakan
// sebagai item menu utama dan menu laporan
public class MenuModel extends BaseModel {

    // id menu
    public int Id;

    // icon yg digunakan
    public int Icon;

    // text untuk menu
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


    // static fungsi yg digunakan untuk
    // menginisialisasi menu utama
    public static ArrayList<MenuModel> getMainMenuList(Context c) {
        ArrayList<MenuModel> menus = new ArrayList<>();
        menus.add(new MenuModel(ID_FOOD_EXPENSE, R.drawable.food,c.getString(R.string.food_expense)));
        menus.add(new MenuModel(ID_TRANSPORT_EXPENSE, R.drawable.bus,c.getString(R.string.transport_expense)));
        menus.add(new MenuModel(ID_COMMON_NEED_EXPENSE, R.drawable.commond_need,c.getString(R.string.common_expense)));
        menus.add(new MenuModel(ID_REPORT, R.drawable.report_menu,c.getString(R.string.menu_report)));
        return menus;
    }


    public static final int ID_REPORT_LIST = 0;
    public static final int ID_REPORT_LINE_DIAGRAM = 1;
    public static final int ID_REPORT_PIE_DIAGRAM = 2;
    public static final int ID_REPORT_WATERFALL_DIAGRAM = 3;

    // static fungsi yg digunakan untuk
    // menginisialisasi menu laporan
    public static ArrayList<MenuModel> getReportMenuList(Context c) {
        ArrayList<MenuModel> menus = new ArrayList<>();
        menus.add(new MenuModel(ID_REPORT_LIST, R.drawable.list_diagram,c.getString(R.string.list_report_title_menu)));
        menus.add(new MenuModel(ID_REPORT_LINE_DIAGRAM, R.drawable.line_diagram,c.getString(R.string.line_diagram_report_title_menu)));
        menus.add(new MenuModel(ID_REPORT_PIE_DIAGRAM, R.drawable.pie,c.getString(R.string.pie_diagram_report_title_menu)));
        menus.add(new MenuModel(ID_REPORT_WATERFALL_DIAGRAM, R.drawable.waterfall_diagram,c.getString(R.string.waterfall_diagram_report_title_menu)));
        return menus;
    }
}
