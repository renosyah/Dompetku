package com.syahputrareno975.dompetku.util;

import static com.syahputrareno975.dompetku.model.menu.MenuModel.ID_COMMON_NEED_EXPENSE;
import static com.syahputrareno975.dompetku.model.menu.MenuModel.ID_FOOD_EXPENSE;
import static com.syahputrareno975.dompetku.model.menu.MenuModel.ID_TRANSPORT_EXPENSE;

public class ExpenseCategory {
    public static final int CATEGORY_NON_EXPENSE = -1;
    public static final int CATEGORY_FOOD_EXPENSE = 0;
    public static final int CATEGORY_TRANSPORT_EXPENSE = 1;
    public static final int CATEGORY_COMMON_NEED_EXPENSE = 2;

    public static int getCategory(int menuCode){
        switch (menuCode){
            case ID_FOOD_EXPENSE:
                return CATEGORY_FOOD_EXPENSE;
            case ID_TRANSPORT_EXPENSE:
                return CATEGORY_TRANSPORT_EXPENSE;
            case ID_COMMON_NEED_EXPENSE:
                return CATEGORY_COMMON_NEED_EXPENSE ;
                default:
                    break;
        }
        return CATEGORY_NON_EXPENSE;
    }
}
