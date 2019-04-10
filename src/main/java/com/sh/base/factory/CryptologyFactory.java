package com.sh.base.factory;

import com.sh.base.cryptology.BaseCryptology;

public class CryptologyFactory {

    private static CryptologyFactory cryptologyFactory;

    private BaseCryptology baseCryptology;

    private CryptologyFactory(){}

    public static synchronized CryptologyFactory getInstance(){
        if (cryptologyFactory == null){
            cryptologyFactory = new CryptologyFactory();
        }
        return cryptologyFactory;
    }

    public BaseCryptology getBaseCryptology() {
        return baseCryptology;
    }

    public void setBaseCryptology(BaseCryptology baseCryptology) {
        this.baseCryptology = baseCryptology;
    }

    public byte[] encode(byte[] data){
        return baseCryptology.encode(data);
    }

    public byte[] decode(byte[] data){
        return baseCryptology.decode(data);
    }

    public boolean decide(byte[] data){return baseCryptology.decide(data);
    }
}
