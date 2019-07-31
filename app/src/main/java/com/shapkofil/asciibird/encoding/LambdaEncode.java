package com.shapkofil.asciibird.encoding;

import java.util.BitSet;

@FunctionalInterface
public interface LambdaEncode {
    char perform(BitSet bitSet);
}
