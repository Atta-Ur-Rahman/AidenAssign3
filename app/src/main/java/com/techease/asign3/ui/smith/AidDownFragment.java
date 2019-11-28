package com.techease.asign3.ui.smith;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techease.asign3.R;
import com.techease.asign3.adapter.ImagesAdapter;
import com.techease.asign3.genrelUtills.AlertUtils;
import com.techease.asign3.genrelUtills.FileUtils;
import com.techease.asign3.genrelUtills.GenrelUtils;
import com.techease.asign3.model.WallpaperDataModel;
import com.techease.asign3.model.WallpaperResponseModel;
import com.techease.asign3.networking.BaseNetworking;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
public class AidDownFragment extends Fragment {


    Bitmap bitmap;
    AidDownViewModel galleryViewModel;
    public static ArrayList<WallpaperDataModel> topListWallpaperDataModels = new ArrayList<>();
    Button btnDownloadImage;
    RecyclerView rvAllImages;
    ImagesAdapter allWallpaperAdapter;
    int anIntPath = 0;
    String path;
    boolean aBooleanImageIsLoaded = false;
    InputStream inputStream;


    ProgressBar pbPercentage;
    TextView tvPercentage;


    Dialog dialog;

    LinearLayout llPercentage;

    private String[] permissions = new String[]{
            Manifest.permission.INTERNET,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel = ViewModelProviders.of(this).get(AidDownViewModel.class);
        View root = inflater.inflate(R.layout.fragment_aid_down, container, false);

        dialog = AlertUtils.createProgressDialog(getActivity());
        btnDownloadImage = root.findViewById(R.id.btnDownloadImage);
        tvPercentage = root.findViewById(R.id.tvProgressPercentage);
        pbPercentage = root.findViewById(R.id.pb_webview);
        llPercentage = root.findViewById(R.id.llPercentage);
        rvAllImages = root.findViewById(R.id.rv_all_images);


        if (GenrelUtils.getSharedPreferences(getActivity()).getBoolean("night_mode", false)) {
            btnDownloadImage.setTextColor(R.color.colorPrimaryDarkDrkMode);
        }


        btnDownloadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (aBooleanImageIsLoaded) {
                    if (anIntPath == 20) {
                        anIntPath = 0;
                    } else {
                        anIntPath = anIntPath + 1;
                        Log.d("imagePath", String.valueOf(topListWallpaperDataModels.get(anIntPath).getPath()));
                        (new SaveWallpaper(getActivity())).execute(topListWallpaperDataModels.get(anIntPath).getPath());
                    }
                } else {
                    Toast.makeText(getActivity(), "please wait......", Toast.LENGTH_SHORT).show();
                }
            }
        });


        if (GenrelUtils.isInternetAvailable(getActivity())) {
            getWallpaperImages(3);
        } else {
            Toast.makeText(getActivity(), "no internet connection", Toast.LENGTH_SHORT).show();

        }

        if (checkPermissions()) {

            FetchImages();
            RecyclerViewFunction();
        }


        return root;
    }

    private ArrayList<String> FetchImages() {

        ArrayList<String> filenames = new ArrayList<String>();
        path = Environment.getExternalStorageDirectory()
                + File.separator + "AidenAssign3Images";
        File directory = new File(path);
        File[] files = directory.listFiles();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String file_name = files[i].getName();
                // you can store name to arraylist and use it later
                filenames.add(file_name);
            }
            Collections.reverse(filenames);

        }

        return filenames;
    }

    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(getActivity(), p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(getActivity(), listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void RecyclerViewFunction() {
        rvAllImages.hasFixedSize();
        rvAllImages.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvAllImages.setItemAnimator(new DefaultItemAnimator());
        rvAllImages.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getActivity()), DividerItemDecoration.VERTICAL));

        rvAllImages.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        allWallpaperAdapter = new ImagesAdapter(FetchImages(), getActivity());
        rvAllImages.setAdapter(allWallpaperAdapter);

    }


    private void getWallpaperImages(int pageId) {


        dialog.show();
        retrofit2.Call<WallpaperResponseModel> wallpaperResponseModelCall1 = BaseNetworking.apiServices().getAllImages(pageId, "random", "16x9");

        wallpaperResponseModelCall1.enqueue(new Callback<WallpaperResponseModel>() {
            @Override
            public void onResponse(Call<WallpaperResponseModel> call, Response<WallpaperResponseModel> response) {


                Log.d("response", String.valueOf(response));


                dialog.dismiss();
                if (response.isSuccessful()) {

                    aBooleanImageIsLoaded = true;
                    topListWallpaperDataModels.addAll(response.body().getData());
                    Log.d("response", String.valueOf(response));

                }
            }

            @Override
            public void onFailure(Call<WallpaperResponseModel> call, Throwable t) {
                aBooleanImageIsLoaded = false;
                dialog.dismiss();
                Log.d("response", String.valueOf(t));

            }
        });

    }


    @SuppressLint("StaticFieldLeak")
    public class SaveWallpaper extends AsyncTask<String, String, String> {
        private Context context;

        public SaveWallpaper(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... args) {
            // TODO Auto-generated method stub

            int count, lengthOfFile = 0;
            try {


                llPercentage.setVisibility(View.VISIBLE);
                URL url = new URL(topListWallpaperDataModels.get(anIntPath).getPath());
                URLConnection connection = url.openConnection();
                connection.connect();
                lengthOfFile = connection.getContentLength();

                bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                inputStream = new BufferedInputStream(url.openStream(), 8192);
                boolean isSaveImage = FileUtils.saveWallPaper(getActivity(), bitmap);
                if (isSaveImage) {


//                    alertDialog.dismiss();
                }
            } catch (IOException e) {
                System.out.println(e);
            }

            Date currentTime = Calendar.getInstance().getTime();
            String dataTime = String.valueOf(currentTime);
            // Environment.DIRECTORY_PICTURES
            File path = Environment.getExternalStoragePublicDirectory("AidenAssign3Images");

            if (!path.exists()) {
                path.mkdirs();
            }
            File imageFile = new File(path, dataTime + ".PNG");
            try {
                FileOutputStream out = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out); // Compress Image


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = inputStream.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lengthOfFile));
                    Log.d("Progress", "Progress: " + (int) ((total * 100) / lengthOfFile));


                    pbPercentage.setProgress((int) ((total * 100) / lengthOfFile));
//                    tvPercentage.setText((int) ((total * 100) / lengthOfFile) + "%");


                    // writing data to file
                    out.write(data, 0, count);
                }

                out.flush();
                out.close();


            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected void onPostExecute(String args) {
//            alertDialog.dismiss();
            Toast.makeText(context, "Image saved in folder AidenAssign3", Toast.LENGTH_SHORT).show();
            RecyclerViewFunction();
            dialog.dismiss();

        }
    }


}

