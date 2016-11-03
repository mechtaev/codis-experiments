
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


(constraint (= (f #x9c8cd8b65d695b32) #x0000004c422c102c))
(constraint (= (f #xaa05a4b655a14067) #xaa05aeb7f5b755e7))
(constraint (= (f #xa0633a79da6e25c7) #xa063ba7bfa7fffef))
(constraint (= (f #x5e4c1d3cd814ecb7) #x0000000e060c0a64))
(constraint (= (f #x86e712b3541c3042) #x86e796f756bf745e))
(constraint (= (f #xc5e170522c56035e) #x0000002020102900))
(constraint (= (f #x0c68eeec80bd08ec) #x0c68eeeceefd88fd))
(constraint (= (f #x0aa7c70e8ea8ee88) #x0aa7cfafcfaeeea8))
(constraint (= (f #x961615572e5e31e5) #x961697573f5f3fff))
(constraint (= (f #xe1ae3ed132a39c70) #x0000001040194088))
(constraint (= (f #x60bc85c00eebc5ea) #x60bce5fc8febcfeb))
(constraint (= (f #xd80344e947e5de04) #xd803dceb47eddfe5))
(constraint (= (f #x0643ede76d3654e0) #x0643efe7edf77df6))
(constraint (= (f #x54600301735d6dce) #x54605761735d7fdf))
(constraint (= (f #x5ee3929d08761216) #x0000000940800a00))
(constraint (= (f #xbe19d076ee69eb74) #x0000004808603075))
(constraint (= (f #xb2cab3e97323e88a) #xb2cab3ebf3ebfbab))
(constraint (= (f #xe3a28514e13e92eb) #xe3a2e7b6e53ef3ff))
(constraint (= (f #x1ea7565a95998589) #x1ea75effd7db9599))
(constraint (= (f #xc97326a1a8e2edee) #xc973eff3aee3edee))
(constraint (= (f #x55c92bb63bd1e2a2) #x55c97fff3bf7fbf3))
(constraint (= (f #x14b7d05e1e33ce45) #x14b7d4ffde7fde77))
(constraint (= (f #x31094dee2a1523be) #x0000000084040211))
(constraint (= (f #x036d211e1372bb7a) #x0000000086008909))
(constraint (= (f #x952ee4cae6e92b6c) #x952ef5eee6ebefed))
(constraint (= (f #x6741ac68bb11cbd3) #x0000001220540045))
(constraint (= (f #xbee5c092c6d48091) #x0000004040604840))
(constraint (= (f #x2c5c1e2b984e3d93) #x00000006040c050c))
(constraint (= (f #x7cba136431e4ec34) #x000000081008b210))
(constraint (= (f #x251474d16ebd0b5e) #x0000001208324885))
(constraint (= (f #xc58bd06070d482b1) #x0000006000282000))
(constraint (= (f #x0deec966935d5a9e) #x00000004b340a209))
(constraint (= (f #xc1aaec8ed13804e7) #xc1aaedaefdbed5ff))
(constraint (= (f #x1a10d0660c6cd5b4) #x0000000800003202))
(constraint (= (f #xedee919be3008eba) #x00000040c5408041))
(constraint (= (f #x2e271519421bb550) #x0000000200800c80))
(constraint (= (f #x9d9cb2759b5472c0) #x9d9cbffdbb75fbd4))
(constraint (= (f #x9961b61c0c2de7a4) #x9961bf7dbe3defad))
(constraint (= (f #xa865de790e224ee9) #xa865fe7dde7b4eeb))
(constraint (= (f #x9e0e83116aa78eb4) #x0000004100010085))
(constraint (= (f #x2c9590d428ed28e5) #x2c95bcd5b8fd28ed))
(constraint (= (f #xcdeba97e6eed649d) #x00000044b5143632))
(constraint (= (f #x156c29d7d83a174d) #x156c3dfff9ffdf7f))
(constraint (= (f #x29bc6c18cd67a3e2) #x29bc6dbced7fefe7))
(constraint (= (f #x5eaccd7167ce6ede) #x000000261022a033))
(constraint (= (f #x21d4d2e700b323a9) #x21d4f3f7d2f723bb))
(constraint (= (f #x34c4760b92038399) #x0000001a000901c1))
(constraint (= (f #xb0287b9a5ee756e9) #xb028fbba7fff5eef))
(constraint (= (f #x0584ce6b02222a6d) #x0584cfefce6b2a6f))
(constraint (= (f #xee9856c6e8005e7a) #x0000002340200024))
(constraint (= (f #x2869b68079b791e6) #x2869bee9ffb7f9f7))
(constraint (= (f #xeeca4d20a15ceb59) #x0000002600008050))
(constraint (= (f #xa3d52b43ce119cec) #xa3d5abd7ef53defd))
(constraint (= (f #xd2a3d669aaeca7ab) #xd2a3d6ebfeedafef))
(constraint (= (f #x57354d7b9bbee672) #x0000002298849d41))
(constraint (= (f #x0e92d6822e242a43) #x0e92de92fea62e67))
(constraint (= (f #x7e9a5eeb97886bad) #x7e9a7efbdfebffad))
(constraint (= (f #x9be21a7b9e6e686b) #x9be29bfb9e7ffe6f))
(constraint (= (f #x7263751879802c82) #x7263777b7d987d82))
(constraint (= (f #x151ee2e08934e66b) #x151ef7feebf4ef7f))
(constraint (= (f #xc4c0eeebee5e5eb8) #x0000006260772527))
(constraint (= (f #xd8b7008ee7533498) #x0000000043000112))
(constraint (= (f #x8ba99ee8467d7a0b) #x8ba99fe9defd7e7f))
(constraint (= (f #x2715a32cd1d75a6b) #x2715a73df3ffdbff))
(constraint (= (f #x62622c83041be8d8) #x0000001001020180))
(constraint (= (f #xd269cde84e6850b8) #x0000006034263420))
(constraint (= (f #x610697b216b057db) #x00000000810b580b))
(constraint (= (f #x8841c3256c55ee85) #x8841cb65ef75eed5))
(constraint (= (f #x60d5260b78e49897) #x000000100090000c))
(constraint (= (f #xee34e564ae3e7052) #x0000007212521210))
(constraint (= (f #x04c54e7063e3840e) #x04c54ef56ff3e7ef))
(constraint (= (f #xe648951e54eb9eab) #xe648f75ed5ffdeeb))
(constraint (= (f #x20ddce58bdbd652c) #x20ddeeddfffdfdbd))
(constraint (= (f #xedb5e67e4e1d3971) #x000000721a230e04))
(constraint (= (f #xa8057a786a19e563) #xa805fa7d7a79ef7b))
(constraint (= (f #xe30627e5dc43d014) #x00000011820220e8))
(constraint (= (f #x4033a41bd1a7b15b) #x0000000009c001c8))
(constraint (= (f #x871412d099177bb1) #x000000010808080c))
(constraint (= (f #xd1490e7e7b7728ce) #xd149df7f7f7f7bff))
(constraint (= (f #x8d020088b3e320b4) #x0000000000004010))
(constraint (= (f #x764116a82759d851) #x0000000b00030400))
(constraint (= (f #x8c153ed79620e2c0) #x8c15bed7bef7f6e0))
(constraint (= (f #x2c71e9742d2e5cee) #x2c71ed75ed7e7dee))
(constraint (= (f #x3ce7d5b5cd96641d) #x0000000a52e2ca22))
(constraint (= (f #xa03c9388e107d096) #x0000004004408060))
(constraint (= (f #x5cd259dedaae59ed) #x5cd25ddedbfedbef))
(constraint (= (f #xd2054bb479e709a4) #xd205dbb57bf779e7))
(constraint (= (f #xed43b166b4b688c1) #xed43fd67b5f6bcf7))
(constraint (= (f #xc580327ea3c2d383) #xc580f7feb3fef3c3))
(constraint (= (f #xc09960cd675ee1c8) #xc099e0dd67dfe7de))
(constraint (= (f #xe9e4e76ea20e2d3e) #x00000070b2510710))
(constraint (= (f #xee10c2500a781eba) #x0000006108012805))
(constraint (= (f #x42d86a5780123b9e) #x0000002128000900))
(constraint (= (f #x0a173bbe85764e0b) #x0a173bbfbffecf7f))
(constraint (= (f #xeb7e35a4aa6baecb) #xeb7efffebfefaeeb))
(constraint (= (f #x77e4edac8aaa15a5) #x77e4ffecefae9faf))
(constraint (= (f #x84247716b8c0121a) #x0000000202180008))
(constraint (= (f #x86a76727de7b503b) #x0000000313a311a8))
(constraint (= (f #x3138b913e045e1c6) #x3138b93bf957e1c7))
(constraint (= (f #x79996a8cdd101b15) #x000000344424000c))
(check-synth)
