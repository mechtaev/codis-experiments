
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


(constraint (= (f #xc9ab9d05dbe46aee) #xc9aba9a095b4c8ac))
(constraint (= (f #x619b749848ec725e) #x0000000000000001))
(constraint (= (f #xbaa53eedc3d30e1e) #x0000000000000001))
(constraint (= (f #x07cbece57059812d) #x0000000000000000))
(constraint (= (f #x202901e68c2e8546) #x202903e91c4cee08))
(constraint (= (f #x73e7351b7bb03c60) #x0000000000000001))
(constraint (= (f #x2e13b0644870623b) #x0000000000000000))
(constraint (= (f #x80ce539482649562) #x80ce5ba1679ddd88))
(constraint (= (f #x732c7eccc017c518) #x732c85ff88049119))
(constraint (= (f #x6e4382be4d33dc3e) #x0000000000000001))
(constraint (= (f #x0e12e8ee12ca6cea) #x0e12e9cf41594e16))
(constraint (= (f #xe5ee999099cb2e18) #xe5eea7ef836437b4))
(constraint (= (f #x8bceae92b97a5730) #x8bceb74fa46382c7))
(constraint (= (f #x00c83a0653d7e5d7) #x0000000000000000))
(constraint (= (f #xc79a68a17dcd7584) #x0000000000000001))
(constraint (= (f #x0e168d41edaa4a33) #x0000000000000000))
(constraint (= (f #x8e86abd30ee5e6e4) #x0000000000000001))
(constraint (= (f #x25010c5ddedbeeb0) #x25010eadefa1cc9d))
(constraint (= (f #xe57abb668e94584e) #xe57ac9be3a4ac137))
(constraint (= (f #xbe500ce3e8c5c078) #xbe5018c8e993ff04))
(constraint (= (f #xc8a15d2d63bac719) #xc8a15d2d63bac719))
(constraint (= (f #xb44b772b6d827de0) #x0000000000000001))
(constraint (= (f #xdcbb51e922394c87) #xdcbb51e922394c87))
(constraint (= (f #x9eb0ee11cc12ea1a) #x0000000000000001))
(constraint (= (f #x39a7ed149da2a75e) #x0000000000000001))
(constraint (= (f #x8303310aaa8e96eb) #x8303310aaa8e96eb))
(constraint (= (f #x909e206b181ee8e6) #x909e2974fa259a67))
(constraint (= (f #x04cc4e4181d6351a) #x0000000000000001))
(constraint (= (f #x8ce0c1d1a977e31c) #x8ce0ca9fb594fdb3))
(constraint (= (f #x84aae96ece6329a9) #x0000000000000000))
(constraint (= (f #xd0eb71cd6ea16a27) #xd0eb71cd6ea16a27))
(constraint (= (f #x5ab29e7ee0e35be1) #x0000000000000000))
(constraint (= (f #x2d146957e4220b8b) #x2d146957e4220b8b))
(constraint (= (f #xdec1848a298d73b1) #xdec1848a298d73b1))
(constraint (= (f #x0491ae72394d6b9e) #x0000000000000001))
(constraint (= (f #xdb813da8c592a2eb) #xdb813da8c592a2eb))
(constraint (= (f #x2209737918869527) #x2209737918869527))
(constraint (= (f #xba3e95ea6e22edbb) #x0000000000000000))
(constraint (= (f #xcd14e56c57d1dc96) #x0000000000000001))
(constraint (= (f #x656152bccbe6cbc3) #x656152bccbe6cbc3))
(constraint (= (f #x19cd2974730e7dd0) #x19cd2b1145a5c500))
(constraint (= (f #xaabee9e63eb3400c) #x0000000000000001))
(constraint (= (f #x9ee7378092c94e70) #x9ee7416f0641579c))
(constraint (= (f #x185d504496ec23d8) #x185d51ca6bf06d46))
(constraint (= (f #x81ce809c616cabd4) #x81ce88b9497671ea))
(constraint (= (f #x44526cb8e609b6ce) #x445270fe0cd5452e))
(constraint (= (f #x80bdee9e9848bc78) #x80bdf6aa7732a5fc))
(constraint (= (f #x55e10888471eab44) #x0000000000000001))
(constraint (= (f #xd7965d20adeceac3) #xd7965d20adeceac3))
(constraint (= (f #xedeb7558b187736a) #xedeb843768dcfe82))
(constraint (= (f #xd959a88747dea524) #x0000000000000001))
(constraint (= (f #x914a2d1a058ee29d) #x914a2d1a058ee29d))
(constraint (= (f #x800542a2926e6db0) #x80054aa2e69896d6))
(constraint (= (f #xd06e4ee61e5bbc0c) #x0000000000000001))
(constraint (= (f #x0b6b27b00b60b17d) #x0b6b27b00b60b17d))
(constraint (= (f #xba37e42ba681b15e) #x0000000000000001))
(constraint (= (f #x928e736e1708dd1b) #x0000000000000000))
(constraint (= (f #x6a13e6087500d4e4) #x0000000000000001))
(constraint (= (f #x04c56c30e1d8bee5) #x0000000000000000))
(constraint (= (f #x69e68b2aeeee6b21) #x0000000000000000))
(constraint (= (f #xa3b000bc2dc831d3) #x0000000000000000))
(constraint (= (f #xac5dabe0a85e2023) #xac5dabe0a85e2023))
(constraint (= (f #x82ee2dc320ab9e3e) #x0000000000000001))
(constraint (= (f #x98589dd47c87c9ae) #x9858a75a06651176))
(constraint (= (f #x8dcc1183b44164de) #x0000000000000001))
(constraint (= (f #xc95a5b2cdcc539a9) #x0000000000000000))
(constraint (= (f #xdc7e81db1a05e2d1) #xdc7e81db1a05e2d1))
(constraint (= (f #x6e80cd5be75585ad) #x0000000000000000))
(constraint (= (f #x3472ceee5594c221) #x0000000000000000))
(constraint (= (f #x33b008e4d76924c0) #x0000000000000001))
(constraint (= (f #x8660c11c8ed1eabe) #x0000000000000001))
(constraint (= (f #x473c7582a1b35030) #x473c79f6690b7a4b))
(constraint (= (f #xbb07c25cca5b0744) #x0000000000000001))
(constraint (= (f #x00571e1ecb2661e4) #x0000000000000001))
(constraint (= (f #x90a7e2b405d97c71) #x90a7e2b405d97c71))
(constraint (= (f #xdd2d25eea018b42a) #xdd2d33c172779e2b))
(constraint (= (f #x0e574784809357ec) #x0000000000000001))
(constraint (= (f #x689e7516bb6d5b55) #x689e7516bb6d5b55))
(constraint (= (f #x01c1590747a27421) #x0000000000000000))
(constraint (= (f #x76e76d07abe22cb3) #x0000000000000000))
(constraint (= (f #xde111e3e9ea537cc) #x0000000000000001))
(constraint (= (f #x174ee2eeb3d8e361) #x0000000000000000))
(constraint (= (f #xd4e4253edc68a7a5) #x0000000000000000))
(constraint (= (f #xbaac2b832741e854) #xbaac372de9fa1ac8))
(constraint (= (f #x74118ad1d5b32625) #x0000000000000000))
(constraint (= (f #xde03ee224a48de9c) #xde03fc02892b0340))
(constraint (= (f #xe8b501b0a34119e7) #xe8b501b0a34119e7))
(constraint (= (f #xab06b143ebc32514) #xab06bbf456d763d0))
(constraint (= (f #xc88d4be6bc932797) #x0000000000000000))
(constraint (= (f #x239b92b56495ed76) #x0000000000000001))
(constraint (= (f #x8ecb52d1834c1760) #x0000000000000001))
(constraint (= (f #x61eed5de6161006c) #x0000000000000001))
(constraint (= (f #xe9173e8eed89ed3e) #x0000000000000001))
(constraint (= (f #xde0ceb0372420038) #xde0cf8e440f2375c))
(constraint (= (f #x73530042bcda335e) #x0000000000000001))
(constraint (= (f #xd7e5ce2d081c5ee8) #x0000000000000001))
(constraint (= (f #xd749217b19ba93de) #x0000000000000001))
(constraint (= (f #x9a980d99d27e701e) #x0000000000000001))
(constraint (= (f #xb4a2b39de2e48dea) #xb4a2bee80e1e6c18))
(constraint (= (f #x5d1b2cce7e999a19) #x5d1b2cce7e999a19))
(check-synth)
