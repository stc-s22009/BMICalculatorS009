package jp.suntech.s22009.bmicalculators009;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class SubDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("警告")
                .setMessage("適切な肥満度を求めるには、6歳未満の場合はカウプ指数が、6歳から15歳まではローレル指数が使われます。本アプリはBMIのみに対応しています。")
                .setPositiveButton("確認", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //ボタンが押されたら何もしない
                    }
                });

        return builder.create();
    }
}
