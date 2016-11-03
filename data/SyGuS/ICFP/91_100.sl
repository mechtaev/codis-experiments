
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


(constraint (= (f #x0144e5436b07a7a5) #x0000000000000001))
(constraint (= (f #x25434ee63d67a71a) #xfdabcb119c29858e))
(constraint (= (f #xce6e21cae09eb2ea) #x0000000000000000))
(constraint (= (f #xbc0ae0b91157856a) #x0000000000000000))
(constraint (= (f #xd751c10ee52c6aad) #x0000000000000001))
(constraint (= (f #x90484de2e35b5635) #xf6fb7b21d1ca4a9c))
(constraint (= (f #x5e4682457dee2e95) #xfa1b97dba8211d16))
(constraint (= (f #x4db1260bc0520394) #xfb24ed9f43fadfc6))
(constraint (= (f #x12b385282b3bcd54) #xfed4c7ad7d4c432a))
(constraint (= (f #x1bec81c8213dcd40) #x0000000000000000))
(constraint (= (f #x18e56d5838c045aa) #x0000000000000000))
(constraint (= (f #xd02a2a67e82ee389) #x0000000000000001))
(constraint (= (f #xbb282e5e8d988e1e) #xf44d7d1a1726771e))
(constraint (= (f #x4bebea9aedd00d26) #x0000000000000000))
(constraint (= (f #xe70b27d81e4862e9) #x0000000000000001))
(constraint (= (f #x7deb985edeea3b67) #x0000000000000001))
(constraint (= (f #x03d91acd2ddab5d9) #xffc26e532d2254a2))
(constraint (= (f #x8eddc3aa3aaccd19) #xf71223c55c55332e))
(constraint (= (f #x815e3c7eecd40c04) #x0000000000000000))
(constraint (= (f #x32336ca745997d1e) #xfcdcc9358ba6682e))
(constraint (= (f #xacc46c9c4d5a3d51) #xf533b9363b2a5c2a))
(constraint (= (f #x96d217b9a820cdeb) #x0000000000000001))
(constraint (= (f #x98bd6584e9947ac4) #x0000000000000000))
(constraint (= (f #x85cae85c823d8655) #xf7a3517a37dc279a))
(constraint (= (f #x92920d1e9e5b2cc6) #x0000000000000000))
(constraint (= (f #x3c6c988a99592aeb) #x0000000000000001))
(constraint (= (f #xeeeab79b68745c41) #x0000000000000001))
(constraint (= (f #x967d7ca1d27e026e) #x0000000000000000))
(constraint (= (f #xbbc9c1786dec2e01) #x0000000000000001))
(constraint (= (f #x64316dee1e1ad04e) #x0000000000000000))
(constraint (= (f #xa36a7c389e148e4c) #x0000000000000000))
(constraint (= (f #xb1277615735e6e2e) #x0000000000000000))
(constraint (= (f #xea6c68dd8c75b43e) #xf15939722738a4bc))
(constraint (= (f #x35a72294eec3767a) #xfca58dd6b113c898))
(constraint (= (f #xdb119898b563b972) #xf24ee67674a9c468))
(constraint (= (f #x2e86960063481b3c) #xfd17969ff9cb7e4c))
(constraint (= (f #x444c8a0a24d2a2e0) #x0000000000000000))
(constraint (= (f #x7cd4c1a259116a0e) #x0000000000000000))
(constraint (= (f #x4dee61e79deb34e5) #x0000000000000001))
(constraint (= (f #xd1a0e5ee7918bbdb) #xf2e5f1a1186e7442))
(constraint (= (f #x3a60e60c835d4b20) #x0000000000000000))
(constraint (= (f #x7ad68be4c7940d1d) #xf8529741b386bf2e))
(constraint (= (f #x37c94286213e9e54) #xfc836bd79dec161a))
(constraint (= (f #x6630ce4e2d3372ea) #x0000000000000000))
(constraint (= (f #x51b994229e3b1414) #xfae466bdd61c4ebe))
(constraint (= (f #x6387622de529710c) #x0000000000000000))
(constraint (= (f #x435ecbbcb430a1ca) #x0000000000000000))
(constraint (= (f #xabee20a1eea4c6e8) #x0000000000000000))
(constraint (= (f #x7562d314ee8485ee) #x0000000000000000))
(constraint (= (f #x5b56a95d21307ac5) #x0000000000000001))
(constraint (= (f #xc2181a59e9bce57d) #xf3de7e5a616431a8))
(constraint (= (f #x677573b6615956a1) #x0000000000000001))
(constraint (= (f #x40ee95427a39019c) #xfbf116abd85c6fe6))
(constraint (= (f #x2a6ec6482da162bb) #xfd59139b7d25e9d4))
(constraint (= (f #x0334a5b49968ebc9) #x0000000000000001))
(constraint (= (f #x67978351d7eb2ee1) #x0000000000000001))
(constraint (= (f #x4c328e5d6ce42e01) #x0000000000000001))
(constraint (= (f #xee6deb604aeb318c) #x0000000000000000))
(constraint (= (f #x2885c0b55373341b) #xfd77a3f4aac8ccbe))
(constraint (= (f #x8eb1369b6e3139ec) #x0000000000000000))
(constraint (= (f #xad3d22192e1e9652) #xf52c2dde6d1e169a))
(constraint (= (f #xd8dde637482bc4e8) #x0000000000000000))
(constraint (= (f #x329116e3580270c0) #x0000000000000000))
(constraint (= (f #xa5ea047e6e6027ce) #x0000000000000000))
(constraint (= (f #xebe691dc22ac30e5) #x0000000000000001))
(constraint (= (f #x5209498e8e08eacb) #x0000000000000001))
(constraint (= (f #x394431ee8e241bee) #x0000000000000000))
(constraint (= (f #x6b6ee557d592c0ea) #x0000000000000000))
(constraint (= (f #x9346d8a57bd42a5c) #xf6cb9275a842bd5a))
(constraint (= (f #x461d43a3313e5c4e) #x0000000000000000))
(constraint (= (f #x6a50ba17443d0514) #xf95af45e8bbc2fae))
(constraint (= (f #xe1e57c5892ee3cab) #x0000000000000001))
(constraint (= (f #xae852d3ccbc662eb) #x0000000000000001))
(constraint (= (f #x26a096eb39e75424) #x0000000000000000))
(constraint (= (f #x094e86c28bee163b) #xff6b1793d7411e9c))
(constraint (= (f #xe0c37e3eed9cb22a) #x0000000000000000))
(constraint (= (f #xdc82b9de8436ea16) #xf237d46217bc915e))
(constraint (= (f #x39c553a917096a7e) #xfc63aac56e8f6958))
(constraint (= (f #x8dd37db22ed23857) #xf722c824dd12dc7a))
(constraint (= (f #x952db77e84189a9b) #xf6ad248817be7656))
(constraint (= (f #xba7cb126e372eee8) #x0000000000000000))
(constraint (= (f #x8e348975e4b5decd) #x0000000000000001))
(constraint (= (f #x73edeb377b0ea16c) #x0000000000000000))
(constraint (= (f #x7562d88aa2abe013) #xf8a9d27755d541fe))
(constraint (= (f #x226be04e6e4eb0e3) #x0000000000000001))
(constraint (= (f #x4e267b0680503c5a) #xfb1d984f97fafc3a))
(constraint (= (f #x85e96842e1b98636) #xf7a1697bd1e4679c))
(constraint (= (f #x135dc28a8d04b35b) #xfeca23d7572fb4ca))
(constraint (= (f #x88a0760dc61ba2de) #xf775f89f239e45d2))
(constraint (= (f #x7937ae91e0a7ba46) #x0000000000000000))
(constraint (= (f #x6240b4602ae415eb) #x0000000000000001))
(constraint (= (f #xe1ae00a0806e2325) #x0000000000000001))
(constraint (= (f #xa1d4ece45a92eee1) #x0000000000000001))
(constraint (= (f #x93aea8023bec2aa5) #x0000000000000001))
(constraint (= (f #x64ed608ab1003544) #x0000000000000000))
(constraint (= (f #xaba0ba7a575b51a3) #x0000000000000001))
(constraint (= (f #x05ae40e5eedde6c8) #x0000000000000000))
(constraint (= (f #xc90adb4d56ae671a) #xf36f524b2a95198e))
(constraint (= (f #xba35ed7b4d01d757) #xf45ca1284b2fe28a))
(constraint (= (f #xb04d669eeec3abcc) #x0000000000000000))
(check-synth)
