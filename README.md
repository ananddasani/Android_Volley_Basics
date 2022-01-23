# Android_Volley_Basics
Using Volley Library to fetch data from the API

# Code

#### 1st Activity 
```
Button button;
TextInputLayout textInputEditText;

PRDownloader.initialize(getApplicationContext());

         button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if permission granted then download the file
                Dexter.withContext(MainActivity.this)
                        .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                //extract the url
                                String givenUrl = textInputEditText.getEditText().getText().toString();
                                if (!givenUrl.equals("")) {
                                    String fileName = URLUtil.guessFileName(givenUrl, null, null);
                                    downloadFile(givenUrl, fileName, v);
                                } else
                                    Toast.makeText(MainActivity.this, "Please Give URL", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                                Toast.makeText(MainActivity.this, "Permission Denied", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });
        
        private void downloadFile(String givenUrl, String fileName, View view) {

        //setting the progress bar
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Downloading...");
        progressDialog.setCancelable(false);
        progressDialog.onBackPressed();
        progressDialog.show();

        File fileDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);

        PRDownloader.download(givenUrl, fileDir.toString(), fileName)
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {

                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {

                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {

                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {

                        //convert progress into progress
                        long percent = progress.currentBytes * 100 / progress.totalBytes;
                        progressDialog.setMessage("Downloaded " + percent + "%");
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        progressDialog.dismiss();

                        //show an snackBar on download complete
                        Snackbar.make(view, fileName + " Downloaded", Snackbar.LENGTH_LONG)
                                .setAction("Downloads", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        //show the recently downloaded
//                                        startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));

                                        Uri selectedUri = Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString());
                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                        intent.setDataAndType(selectedUri, "resource/folder");

                                        startActivity(intent);
                                    }
                                }).show();

//                        Toast.makeText(MainActivity.this, fileName + " Downloaded", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Error error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Error in Download", Toast.LENGTH_SHORT).show();
                    }
                });
    }

```

# App Highlight

<img src="app_images/PRDownloader Code.png" /><br>

<img src="app_images/PRDownloader App1.png" width="300" /> <img src="app_images/PRDownloader App3.png" width="300" /> <img src="app_images/PRDownloader App2.png" width="300" /><br>
