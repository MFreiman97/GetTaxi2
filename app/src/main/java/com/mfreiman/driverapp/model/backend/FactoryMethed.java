package com.mfreiman.driverapp.model.backend;

import com.mfreiman.driverapp.model.datasource.FireBase_DBManager;

import java.util.List;

public class FactoryMethed {
    static DB_manager manager = null;

    public static DB_manager getManager() {
        if (manager == null)
            manager = new FireBase_DBManager();

        return manager;
    }

}
