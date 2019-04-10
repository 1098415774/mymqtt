package com.sh.base.cryptology;

public interface BaseCryptology {

    byte[] encode(byte[] data);

    byte[] decode(byte[] data);

    boolean decide(byte[] data);
}
