package pers.zforw.empmgr.main;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;

/**
 * @version: 1.0
 * @author: zforw
 * @date: 2021/06/01 9:12 上午
 * @project: Basic
 * @description:
 */
public class digitVerifyListener implements VerifyListener {
    @Override
    public void verifyText(VerifyEvent verifyEvent) {
        verifyEvent.doit = "0123456789".contains(verifyEvent.text);
    }
}
