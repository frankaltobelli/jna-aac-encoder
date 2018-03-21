# jna-aac-encoder

[![Build Status](https://travis-ci.org/sheinbergon/jna-aac-encoder.svg?branch=master)](https://travis-ci.org/sheinbergon/jna-aac-encoder) [![Coverage Status](https://coveralls.io/repos/github/sheinbergon/jna-aac-encoder/badge.svg)](https://coveralls.io/github/sheinbergon/jna-aac-encoder) [![License: LGPL v3](https://img.shields.io/badge/License-LGPL%20v3-blue.svg)](https://www.gnu.org/licenses/lgpl-3.0) [![Maven Central](https://maven-badges.herokuapp.com/maven-central/org.sheinbergon/jna-aac-encoder/badge.svg)](https://maven-badges.herokuapp.com/maven-central/org.sheinbergon/jna-aac-encoder)

                                                                                                                                                                                                                                                                                                 
This library provides AAC encoding capabilities for the JVM.
It utilizes the [FDK AAC](https://github.com/mstorsjo/fdk-aac) library via JNA in order to do so.

## License
**Important!** While this library uses LGPL-3, please see
the [FDK AAC license](NOTICE) for additional information
regarding re/distribution and licensing limitations

## Usage

#### Java AudioSystem
```java
AudioInputStream input = AudioSystem.getAudioInputStream(...);
File output = new File(...);
AudioSystem.write(input, AACFileTypes.AAC_LC, output);
```

#### Limitations
Currently, only pcm_s16le WAV input is supported, meaning:
* Sample size - 16 bit(signed)
* WAV format - PCM
* Byte order - Little Endian

While this seems to the common raw-audio formatting, it's important
to note that providing input audio with different formatting will cause
the encoding process to fail. 

Additional restrictions:
* A maximum of 6 audio input/output channels
* Only AAC-LC(**L**ow **C**omplaxity) encoding profile is suuported  


## Roadmap
* Support for AAC HE & HEv2.
* Improved lower-level interface(with examples).
* Performance tests/comparison (JMH).
* AAC Decoding ???