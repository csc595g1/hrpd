package edu.depaul.csc595.jarvis.community;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import edu.depaul.csc595.jarvis.R;

/**
 * Created by Ed on 3/13/2016.
 */
public class ReplyDialogFragment extends DialogFragment{

    public String resultText;
    public static EditText reply;
    static OnReplyDialogResultListener onReplyDialogResult;
    //private OnReplyDialogResultListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_post_comment,null);
        reply = (EditText) v.findViewById(R.id.replyFieldContent);
        builder.setView(v)
                .setPositiveButton("Post",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id){
                        //handle submitted text
                        resultText = reply.getText().toString();
                        setDialogResult();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ReplyDialogFragment.this.getDialog().cancel();
                    }
                });
        //reply = (EditText) builder.
        return builder.create();
    }

    public void setDialogResult(){
        resultText = reply.getText().toString();
        this.onReplyDialogResult.onPost(resultText);
    }

    public interface OnReplyDialogResultListener{
        public abstract void onPost(String content);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.onReplyDialogResult = (OnReplyDialogResultListener) activity;
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnCompleteListener");
        }
    }
}
