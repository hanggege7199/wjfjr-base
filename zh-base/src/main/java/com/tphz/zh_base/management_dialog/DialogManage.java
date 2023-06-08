package com.tphz.zh_base.management_dialog;

import android.content.DialogInterface;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 管理Dialog
 */
public class DialogManage implements DialogInterface.OnDismissListener {
    private static DialogManage instance;

    private DialogManageDismiss dialogManageDismiss;

    public void setDialogManageDismiss(DialogManageDismiss dialogManageDismiss) {
        this.dialogManageDismiss = dialogManageDismiss;
    }

    private DialogManage() {
        mThreadQueue = new ArrayList<>();
    }

    public static DialogManage getInstance() {
        if (instance == null) {
            synchronized (DialogManage.class) {
                if (instance == null) {
                    return instance = new DialogManage();
                }
            }
        }
        return instance;
    }

    //当前展示dialog
    private DialogEntity currentDialog;

    //队列池
    private final List<Object> mThreadQueue;


    public void destroy() {
        if (currentDialog != null) {
            currentDialog = null;
        }
        mThreadQueue.clear();
        instance = null;
    }


    public void showDialog(DialogEntity dialogEntity) {
        if (dialogEntity.getDialog() == null)
            throw new NullPointerException("DialogEntity in dialog is null");
        boolean currentSet = isCurrentSet(dialogEntity);
        if (currentSet) {
            setAddQueue(dialogEntity);
        }

    }

    /**
     * 添加顺序dialog
     *
     * @param dialogEntity
     */
    private void setAddQueue(DialogEntity dialogEntity) {
        mThreadQueue.add(dialogEntity);
        if (mThreadQueue.size() > 0) {
            Object[] objects = mThreadQueue.toArray();
            Arrays.sort(objects, new QueueComparator());
            List<Object> list = Arrays.asList(objects);
            mThreadQueue.clear();
            mThreadQueue.addAll(list);
        }
    }


    // 实现Comparator接口
    class QueueComparator implements Comparator<Object> {

        @Override
        public int compare(Object o1, Object o2) {
            /*
             * 如果o1小于o2，我们就返回正值，如果o1大于o2我们就返回负值， 这样颠倒一下，就可以实现降序排序了,反之即可自定义升序排序了
             */
            DialogEntity o1dialogEntity = (DialogEntity) o1;
            DialogEntity o2dialogEntity = (DialogEntity) o2;
            return o1dialogEntity.getLevel() - o2dialogEntity.getLevel();
        }
    }

    /**
     * 设置dialog消失消息循环机制
     *
     * @param
     */
    private void setDialogDismiss() {
        currentDialog.getDialog().setOnDismissListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        if (dialogManageDismiss != null) {
            dialogManageDismiss.dialogDismiss(currentDialog);
        }
        if (mThreadQueue.size() > 0) {
            currentDialog = (DialogEntity) mThreadQueue.get(0);
            currentDialog.getDialog().setOnDismissListener(this);
            currentDialog.getDialog().show();
            mThreadQueue.remove(0);
        } else {//null
            currentDialog = null;
        }
    }

    /**
     * 设置为当前dialog，当前已有触发加入队列
     *
     * @return true 当前有，false 当前没有
     */
    private boolean isCurrentSet(DialogEntity dialogEntity) {
        if (currentDialog == null) {
            currentDialog = dialogEntity;
            setDialogDismiss();
            currentDialog.getDialog().show();
            return false;
        } else {
            //当前dialog校验
            return !currentDialog.getDialog().equals(dialogEntity.getDialog());
        }
    }


}
