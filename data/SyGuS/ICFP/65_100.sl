
(set-logic BV)

(define-fun shr1 ((x (BitVec 64))) (BitVec 64) (bvlshr x #x0000000000000001))
(define-fun shr4 ((x (BitVec 64))) (BitVec 64) (bvlshr x #x0000000000000004))
(define-fun shr16 ((x (BitVec 64))) (BitVec 64) (bvlshr x #x0000000000000010))
(define-fun shl1 ((x (BitVec 64))) (BitVec 64) (bvshl x #x0000000000000001))
(define-fun if0 ((x (BitVec 64)) (y (BitVec 64)) (z (BitVec 64))) (BitVec 64) (ite (= x #x0000000000000001) y z))

(synth-fun f ( (x (BitVec 64))) (BitVec 64)
(

(Start (BitVec 64) (#x0000000000000000 #x0000000000000001 x (bvnot Start)
                    (shl1 Start)
 		    (shr1 Start)
		    (shr4 Start)
		    (shr16 Start)
		    (bvand Start Start)
		    (bvor Start Start)
		    (bvxor Start Start)
		    (bvadd Start Start)
		    (if0 Start Start Start)
 ))
)
)


(constraint (= (f #x492e2e4ec003ea32) #x492e2adc22e70632))
(constraint (= (f #x46eb214ad6a4507e) #x46eb252464b0fd14))
(constraint (= (f #x9ee66ec7d4d24e8b) #x9ee66ec7d4d24e8b))
(constraint (= (f #x80d61d73c8c48e1d) #x7f29e28c373b71e2))
(constraint (= (f #x4e5ead7672c30712) #x4e5ea9939814603e))
(constraint (= (f #x3e26e93159e6e919) #xc1d916cea61916e6))
(constraint (= (f #x1cd305ea0dda3b59) #xe32cfa15f225c4a6))
(constraint (= (f #x4dd275235e579eb4) #xb22d8adca1a8614b))
(constraint (= (f #x2cecd43c813b072d) #xd3132bc37ec4f8d2))
(constraint (= (f #x8e9d4ee13bc0364e) #x8e9d4608ef2e25f2))
(constraint (= (f #xe36747a4eeeb3d82) #xe36749929a91736c))
(constraint (= (f #xad82b2780d7eede8) #x527d4d87f2811217))
(constraint (= (f #xe7522dab9b9c40c3) #xe7522dab9b9c40c3))
(constraint (= (f #x623e0de06e2ed3de) #x623e0bc38ef0d53c))
(constraint (= (f #x9e8bbc5c7b106c9e) #x9e8bb5b4c0d5ab2f))
(constraint (= (f #x90453a78949594d1) #x6fbac5876b6a6b2e))
(constraint (= (f #xe906132067aa92ca) #xe9061db0069894b0))
(constraint (= (f #xae70eb4e32d2e4c6) #xae70e1a93c6607eb))
(constraint (= (f #x9d2ee47bbb7a1db4) #x62d11b844485e24b))
(constraint (= (f #x4d455031874e2e29) #xb2baafce78b1d1d6))
(constraint (= (f #x589debc6c918a43e) #x589dee4f17a4c8af))
(constraint (= (f #xe98e46644c95e0e3) #xe98e46644c95e0e3))
(constraint (= (f #x6db54784619439e3) #x6db54784619439e3))
(constraint (= (f #xdbe8996700760551) #x24176698ff89faae))
(constraint (= (f #x93dc0b039ea8b23e) #x93dc023e5e188bd4))
(constraint (= (f #xd8b7c8a0c748a97a) #xd8b7c52bbbc2a50e))
(constraint (= (f #x2c7eb5aeb40d4649) #xd3814a514bf2b9b6))
(constraint (= (f #x22bc9ca46c5b740a) #x22bc9e8fa59132cf))
(constraint (= (f #x56145b3e3c7569a8) #xa9eba4c1c38a9657))
(constraint (= (f #x6e5d4154ba7e8ee6) #x6e5d47b16e6bc541))
(constraint (= (f #xc74d84600dce7485) #x38b27b9ff2318b7a))
(constraint (= (f #xeb7a8e46d09510b0) #x148571b92f6aef4f))
(constraint (= (f #xc7ca2479ec704de4) #x3835db86138fb21b))
(constraint (= (f #x2837ee30c6e15cb4) #xd7c811cf391ea34b))
(constraint (= (f #xd9722bda177d4e3d) #x268dd425e882b1c2))
(constraint (= (f #xb5a7bea258e3ee1e) #xb5a7b5f82309cb90))
(constraint (= (f #xa103511793a95b83) #xa103511793a95b83))
(constraint (= (f #xe4ec082a8cbddc2b) #xe4ec082a8cbddc2b))
(constraint (= (f #xea587800cdb855a1) #x15a787ff3247aa5e))
(constraint (= (f #x83e457137e1aedbd) #x7c1ba8ec81e51242))
(constraint (= (f #x4c0d982146505b34) #xb3f267deb9afa4cb))
(constraint (= (f #x6e3eed6719b2cb8c) #x91c11298e64d3473))
(constraint (= (f #xaac9775d871a41be) #xaac97df1106f99cf))
(constraint (= (f #xe61ee5e27c25b605) #x19e11a1d83da49fa))
(constraint (= (f #x73d16bce413ec26a) #x73d16cf357822679))
(constraint (= (f #x4dc25991e126e040) #xb23da66e1ed91fbf))
(constraint (= (f #x1d715e8be276b8ea) #x1d715f5cf79e06cd))
(constraint (= (f #x8ea68951ae5a8a45) #x715976ae51a575ba))
(constraint (= (f #xb66c1d1e454d6ee1) #x4993e2e1bab2911e))
(constraint (= (f #x5b32c7db17461c18) #xa4cd3824e8b9e3e7))
(constraint (= (f #x399841b604ed6871) #xc667be49fb12978e))
(constraint (= (f #xd2e11c24bca9e04d) #x2d1ee3db43561fb2))
(constraint (= (f #x2ea537d102e3d193) #x2ea537d102e3d193))
(constraint (= (f #x2e1b65bbeb0350cb) #x2e1b65bbeb0350cb))
(constraint (= (f #x3ed56beeea0be573) #x3ed56beeea0be573))
(constraint (= (f #x46b0ee719abcc25a) #x46b0ea1a945bdbf1))
(constraint (= (f #x0716ee2ee3e93b49) #xf8e911d11c16c4b6))
(constraint (= (f #xbe3555539050c1a1) #x41caaaac6faf3e5e))
(constraint (= (f #xd10a3e286e8270e2) #xd10a3338cd60f60a))
(constraint (= (f #x842419e4be9e12ae) #x842411a6ff005947))
(constraint (= (f #x7d5be0d4626842a6) #x7d5be701dc650480))
(constraint (= (f #xd29d62c113e8252b) #xd29d62c113e8252b))
(constraint (= (f #x8e6984ee7613ec56) #x8e698c08ee5d0b37))
(constraint (= (f #x91dda5b4bd328761) #x6e225a4b42cd789e))
(constraint (= (f #x6cd1592770b5b8e8) #x932ea6d88f4a4717))
(constraint (= (f #x3e778a5bd3b61e32) #x3e7789bcab13a309))
(constraint (= (f #x84c386e56be69dae) #x84c38ea95388cb10))
(constraint (= (f #x4ad1eb49d477e4b7) #x4ad1eb49d477e4b7))
(constraint (= (f #x3e7ea8d0d5b0e452) #x3e7eab373f3de909))
(constraint (= (f #x86a14ca2d9830266) #x86a144c8cd492ffe))
(constraint (= (f #x941545edce4e9d08) #x6beaba1231b162f7))
(constraint (= (f #x8914c379d8bc1da7) #x8914c379d8bc1da7))
(constraint (= (f #x8235e38eec12aa9c) #x7dca1c7113ed5563))
(constraint (= (f #x1e54a318d999e8e3) #x1e54a318d999e8e3))
(constraint (= (f #x3739b775da5093b6) #x3739b4064127ce13))
(constraint (= (f #xe76409d3a50550e1) #x189bf62c5afaaf1e))
(constraint (= (f #x8a63c16d6921e8e0) #x759c3e9296de171f))
(constraint (= (f #x912edc0238d526d8) #x6ed123fdc72ad927))
(constraint (= (f #x2178e6a50ca168c0) #xde87195af35e973f))
(constraint (= (f #xee3e455505de0c45) #x11c1baaafa21f3ba))
(constraint (= (f #xee1e196cbedd9e67) #xee1e196cbedd9e67))
(constraint (= (f #x2a4330bca67dbdd0) #xd5bccf435982422f))
(constraint (= (f #x30e89d432b51e426) #x30e89e4da285d693))
(constraint (= (f #x62e54cc34448b102) #x62e54aed10848546))
(constraint (= (f #x136e0bb7bca1e3b8) #xec91f448435e1c47))
(constraint (= (f #xd6607aee78678ae9) #x299f851187987516))
(constraint (= (f #x6371674c22084658) #x9c8e98b3ddf7b9a7))
(constraint (= (f #x2e51ea72e187e8d3) #x2e51ea72e187e8d3))
(constraint (= (f #x52e9e677b76bb34c) #xad16198848944cb3))
(constraint (= (f #x4ed0a9929e51a0d7) #x4ed0a9929e51a0d7))
(constraint (= (f #xc27481db1702c8e9) #x3d8b7e24e8fd3716))
(constraint (= (f #xd96dee343b40ae3e) #xd96de3a2e5a3ed8a))
(constraint (= (f #x046e7599075d7dac) #xfb918a66f8a28253))
(constraint (= (f #x4d6cd3e65a107ba6) #x4d6cd730972e1e07))
(constraint (= (f #x84103e3703a293a7) #x84103e3703a293a7))
(constraint (= (f #x741e8eb58b419d08) #x8be1714a74be62f7))
(constraint (= (f #x3ae291eab1be71b1) #xc51d6e154e418e4e))
(constraint (= (f #x717a9391eb607d0e) #x717a9486425963b8))
(constraint (= (f #x6bb1cca1e8e471da) #x6bb1ca1af42e6f54))
(constraint (= (f #xee84e049d1e6e55e) #xee84eea19fe27840))
(check-synth)
