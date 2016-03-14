package edu.depaul.csc595.jarvis.community;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
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
public class PostDialogFragment extends DialogFragment {

    public String resultText;
    public static EditText reply;
    static OnPostDialogResultListener onPostDialogResult;

    public static PostDialogFragment newInstance(int num){
        PostDialogFragment dialogFragment = new PostDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("num", num);
        dialogFragment.setArguments(bundle);

        return dialogFragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
       // this.onPostDialogResult = (OnPostDialogResultListener) getTargetFragment();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_make_post,null);
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
                        PostDialogFragment.this.getDialog().cancel();
                    }
                });
        //reply = (EditText) builder.
        return builder.create();
    }

    public void setDialogResult(){
        resultText = reply.getText().toString();
        this.onPostDialogResult.onPost(resultText);
    }

    public interface OnPostDialogResultListener{
        public abstract void onPost(String content);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            //this.onPostDialogResult = (OnPostDialogResultListener) activity;
            this.onPostDialogResult = (OnPostDialogResultListener) getTargetFragment();
        } catch (final ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnPostDialogResultListener");
        }
    }
}
