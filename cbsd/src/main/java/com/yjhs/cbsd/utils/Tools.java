package com.yjhs.cbsd.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    public static boolean checkConnection(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null && activeNetInfo.isConnected()) {
            return true;
        }
        return false;
    }


    public static int timeCompare(String startTime, String endTime){
        int i=0;
   //注意：传过来的时间格式必须要和这里填入的时间格式相同
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = dateFormat.parse(startTime);//开始时间
            Date date2 = dateFormat.parse(endTime);//结束时间
            // 1 结束时间小于开始时间 2 开始时间与结束时间相同 3 结束时间大于开始时间
            if (date2.getTime()<date1.getTime()){
                //结束时间小于开始时间
                i= 1;
            }else if (date2.getTime()==date1.getTime()){
                //开始时间与结束时间相同
                i= 2;
            }else if (date2.getTime()>date1.getTime()){
                //结束时间大于开始时间
                i= 3;
            }
        } catch (Exception e) {

        }
        return  i;
    }


    public static Date stepDay(Date sourceDate, int day) {
        java.util.Calendar c = java.util.Calendar.getInstance();
        c.setTime(sourceDate);
        c.set(java.util.Calendar.DATE, c.get(Calendar.DATE)+day);

        return c.getTime();
    }

    public static int getScreenWidth (Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int width = dm.widthPixels;
        return width;
    }

    public static int getScreenHeight (Context context){
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        int height = dm.heightPixels;
        return height;
    }

    public static void closeInputWindow(Activity activity) {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity
                            .getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {

        }
    }
    public static String safeString(TextView tv) {
        String source = tv.getText().toString();
        if (source.length() == 0) {
            return "";
        } else {
            String re = source.trim().replace("}", "").replace("{", "")
                    .replace("<", "").replace(">", "").replace("]", "")
                    .replace("[", "").replace("\"", "");
            return re;
        }
    }

    public static String safeString(double str) {
            return str+"";
    }
    public static String safeString(int str) {
        return str+"";
    }
    public static String safeString(EditText et) {
        String source = et.getText().toString();
        if (source.length() == 0) {
            return "";
        } else {
            String re = source.trim().replace("}", "").replace("{", "")
                    .replace("<", "").replace(">", "").replace("]", "")
                    .replace("[", "").replace("\"", "");
            return re;
        }
    }

    public static String safeString(String str) {
        if (str == null||str.isEmpty()) {
            return "";
        } else {
            if (str.equals("null")) {
                return "";
            }else {
                return str;
            }
        }
    }

    public static boolean matchPhone(String phone){
        if(phone==null||"".equals(phone)){
            return false;
        }
        Pattern p = Pattern.compile("^1[3|4|5|7|8|9]\\d{9}$");
        Matcher m = p.matcher(phone);
        return m.matches();

        //return true;
    }


    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     *            （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 获取利用反射获取类里面的值和名称
     *
     * @param obj
     * @return
     * @throws
     */
    public static HashMap<String, Object> objectToMap(Object obj) throws IllegalAccessException {
        HashMap<String, Object> map = new HashMap<>();
        Class<?> clazz = obj.getClass();
        System.out.println(clazz);
        System.out.println(new Gson().toJson(obj));
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            String fieldName = field.getName();
            String value = String.valueOf(field.get(obj));
            if (value != null && !"null".equals(value) && !"serialVersionUID".equals(fieldName)) {
                map.put(fieldName, value);
            }
        }
        return map;
    }

    public static String createID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static  void loadImage(Context activity, ImageView imageView, String filepath){
        if(filepath==null||filepath.equals("")){
            return;
        }
        File ff=new File(filepath);

        if(ff.exists()==false){
            return;
        }
        BitmapFactory.Options bitmapFactoryOptions = new BitmapFactory.Options();
        bitmapFactoryOptions.inJustDecodeBounds = true;
        bitmapFactoryOptions.inPreferredConfig= Bitmap.Config.ALPHA_8;
        Bitmap bitmap = BitmapFactory.decodeFile(filepath,bitmapFactoryOptions);

        int width = bitmapFactoryOptions.outWidth;
        int height = bitmapFactoryOptions.outHeight;
        if(bitmap!=null) {
            bitmap.recycle();
        }
        int f=1;
        if(width>=height){
            f=width/400;
        }else{
            f=height/400;
        }
        if(f<1){
            f=1;
        }

        bitmapFactoryOptions.inSampleSize=f;
        bitmapFactoryOptions.inJustDecodeBounds = false;
        bitmapFactoryOptions.inPreferredConfig= Bitmap.Config.ARGB_8888;
        bitmap = BitmapFactory.decodeFile(filepath,bitmapFactoryOptions);
        if(bitmap!=null) {
            imageView.setImageBitmap(bitmap);
        }else{
            Toast.makeText(activity,"设置图片失败", Toast.LENGTH_SHORT).show();
        }
    }

    public static byte[] bitmap2Byte(Bitmap bmp, boolean needRecycle) {
        if (bmp == null || "".equals(bmp)) {
            return null;
        }
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] datas = baos.toByteArray();
//        return datas;


        int i;
        int j;
        if (bmp.getHeight() > bmp.getWidth()) {
            i = bmp.getWidth();
            j = bmp.getWidth();
        } else {
            i = bmp.getHeight();
            j = bmp.getHeight();
        }

        if (i > 150) {
            i = 150;
            j = 150;
        }

        Bitmap localBitmap = Bitmap.createBitmap(i, j, Bitmap.Config.RGB_565);
        Canvas localCanvas = new Canvas(localBitmap);

        while (true) {
            localCanvas.drawBitmap(bmp, new Rect(0, 0, i, j), new Rect(0, 0,i, j), null);
            if (needRecycle)
                bmp.recycle();
            ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 100,
                    localByteArrayOutputStream);
            localBitmap.recycle();
            byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
            try {
                localByteArrayOutputStream.close();
                return arrayOfByte;
            } catch (Exception e) {
                //F.out(e);
            }
            i = bmp.getHeight();
            j = bmp.getHeight();
        }
    }


    public static String getSubStringWidth(String str, int width){
        if(TextUtils.isEmpty(str) || width <= 0){
            return "";
        }
        //字符串长度
        int length = str.length();
        Paint paint = new Paint();
        //根据宽度得到字符数量
        int measurennums = paint.breakText(str, true, width, null);
        int txtLength = 2*measurennums;
        //字符数量和长度比较
        if(txtLength>length){
            txtLength = length;
        }
        return str.substring(0, txtLength);
    }

    /**
     * 保存图片到指定路径
     *
     * @param context
     * @param bitmap   要保存的图片
     * @param fileName 自定义图片名称  getString(R.string.app_name) + "" + System.currentTimeMillis()+".png"
     * @return true 成功 false失败
     */
    public static boolean saveImageToGallery(Context context, Bitmap bitmap, String fileName) {
        if (bitmap == null){
            return false;
        }
        // 保存图片至指定路径
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "qrcode";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            //通过io流的方式来压缩保存图片(80代表压缩20%)
            boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //发送广播通知系统图库刷新数据
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            if (isSuccess) {
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }


}
