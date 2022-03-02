package com.zou.xposed_jiemi;

import android.content.SharedPreferences;
import android.widget.TextView;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class pojie_xpose implements IXposedHookLoadPackage {
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        //固定格式
        if(!lpparam.packageName.equals("com.ss.android.ugc.aweme")){
            return;
        }
        XposedBridge.log("进入到测试程序！");

        XposedHelpers.findAndHookMethod(
                //第一关，解锁

                "com.hfdcxy.android.by.test.a",
                lpparam.classLoader,
                "a",
                String.class,
                new XC_MethodHook() {
                    //方法执行前执行
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }
                    //方法执行后执行，改变方法的返回值一定要在方法执行完毕后更改，
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                        XposedBridge.log("a方法的一个参数为："+param.args[0].toString());
                        XposedBridge.log("a方法的返回值为："+param.getResult());
                        //以下为参数
                        //param.arg[0] = "zouzhiqiang"  //如果要修改第一个参数可以直接这样改
                        //param.getResult("fda4fd4afd4fda4fd4fda"); //方法的返回值可以这样修改
                    }
                }
        );
        XposedHelpers.findAndHookMethod(
                //第二关，充值金币
                "com.hfdcxy.android.by.test.b",
                lpparam.classLoader,
                "a",
                SharedPreferences.class,
                TextView.class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                        param.args[2] = 10000;
                        XposedBridge.log("成功充值一万金币");
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                }
        );

    }
}
