package com.tphz.zh_base.management_dialog;

import android.app.Dialog;

public class DialogEntity {
    int level;
    Dialog dialog;

    public DialogEntity(int level, Dialog dialog) {
        this.level = level;
        this.dialog = dialog;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

}
