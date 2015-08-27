package com.paraches.android.mylibrary;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

/**
 * Created by teshi on 15/08/27.
 */
public class PAAlertDialogFragment extends DialogFragment {
    public static final String DIALOG_TITLE_KEY = "dialog_title_key";
    public static final String DIALOG_MESSAGE_KEY = "dialog_message_key";
    public static final String DIALOG_POSITIVE_KEY = "dialog_positive_key";
    public static final String DIALOG_NEGATIVE_KEY = "dialog_negative_key";

    public interface PADialogClickListener {
        void onDialogPositiveButtonClick();
        void onDialogNegativeButtonClick();
    }

    PADialogClickListener listener;

    @NonNull
    public static PAAlertDialogFragment newInstance(@NonNull String title, @Nullable String message, @Nullable String positiveLabel, @Nullable String negativeLabel) {
        PAAlertDialogFragment instance = new PAAlertDialogFragment();

        Bundle arguments = new Bundle();
        arguments.putString(DIALOG_TITLE_KEY, title);
        arguments.putString(DIALOG_MESSAGE_KEY, message);
        arguments.putString(DIALOG_POSITIVE_KEY, positiveLabel);
        arguments.putString(DIALOG_NEGATIVE_KEY, negativeLabel);

        instance.setArguments(arguments);

        return instance;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString(DIALOG_TITLE_KEY);
        String message = getArguments().getString(DIALOG_MESSAGE_KEY);
        String positiveLabel = getArguments().getString(DIALOG_POSITIVE_KEY);
        if (positiveLabel == null) {
            positiveLabel = "OK";
        }
        //  if negativeLabel is null, dialog does NOT have negative button
        //  if dialog need to have negative button, negative button label is requred
        String negativeLabel = getArguments().getString(DIALOG_NEGATIVE_KEY);

        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton(positiveLabel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onDialogPositiveButtonClick();
                            listener = null;    // too bad!
                        }
                        dismiss();
                    }
                });

        if (title != null) {
            alert.setTitle(title);
        }

        if (negativeLabel != null) {
            alert.setNegativeButton(negativeLabel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (listener != null) {
                        listener.onDialogNegativeButtonClick();
                        listener = null;    // too bad!
                    }
                    dismiss();
                }
            });
        }

        return alert.create();
    }

    public void setPADialogClickListener(@Nullable PADialogClickListener listener) {
        this.listener = listener;
    }

    public void removePADialogClickListener() {
        this.listener = null;
    }
}
