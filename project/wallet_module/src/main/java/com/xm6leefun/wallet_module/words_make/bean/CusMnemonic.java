package com.xm6leefun.wallet_module.words_make.bean;

/**
 * @Description: 助记词bean
 * @Author: hhh
 * @CreateDate: 2021/3/26 16:38
 */
public class CusMnemonic {

    private String word;
    private boolean isChecked;
    private boolean isCorrect;
    private int position;

    public CusMnemonic(String word, boolean isChecked, boolean isCorrect, int position) {
        this.word = word;
        this.isChecked = isChecked;
        this.isCorrect = isCorrect;
        this.position = position;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
