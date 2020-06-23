package com.yp.learndemo.fragment;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yp.baseframworklib.base.BaseFragment;

/**
 * @author : yanpu
 * @date : 2020-06-20
 * @description:
 */
public class FragmentUtil {


    public static void commitFragment(@Nullable FragmentManager fragmentManager, int layout, @Nullable BaseFragment fragment){
        if (null!= fragmentManager && null != fragment){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(layout, fragment);
            transaction.commit();
        }
    }

    public static void replaceFragment(@Nullable FragmentManager fragmentManager, int layout, @Nullable BaseFragment fragment){
        if (null!= fragmentManager && null != fragment){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(layout, fragment);
            transaction.commit();
        }
    }

    public static void removeFragment(@Nullable FragmentManager fragmentManager, int layout, @Nullable BaseFragment fragment){
        if (null!= fragmentManager && null != fragment){
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
        }
    }

}
