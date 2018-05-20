package com.example.alowishusad.androidprojekat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import model.Comment;

/**
 * Class that holds static methods for niftyu little thingies
 */
public final class Gadgets {

    /**
     * Static method which shows a Progress Dialog, deprecated but who cares.
     * The method will show the progress dialog and it's up to the user to dismiss() it accordingly
     * @param context - context of the activity to show progress dialog in (can be called by keyword 'this' in activity)
     * @return ProgressDialog
     */
    public static ProgressDialog getProgressDialog(Context context){
        ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMax(100);
        progressDialog.setMessage("Wait...");
        progressDialog.setTitle("");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * Shows a simple OK message
     * @param context context in which to show message
     * @param message message to show
     */
    public static void showSimpleOkDialog(Context context, String message){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(message);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
