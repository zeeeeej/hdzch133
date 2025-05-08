package com.bugull.okstream.iocore;

import java.io.Serializable;

/**
 * Created by cj on 3/12/21.
 */
public interface ISendable extends Serializable {

    /**
     * @return 返回需要发送的字节
     * 例如：
     * private byte[] datas;
     *
     * 设置数据:
     * public void sendTempeture(){
     *     datas=new byte[]{0x55,0x04,0x01,0x50};
     * }
     *
     * public byte[] parse(){
     *     return datas;
     * }
     *
     * 写入器调用：write(iSendable.parse())
     *
     */
    byte[] parse();

}
