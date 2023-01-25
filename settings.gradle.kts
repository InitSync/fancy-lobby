rootProject.name = "fancy-lobby"

include(":api", ":plugin")
includeAdapts(
	 "1_8_R3", "1_9_R2", "1_10_R1",
	 "1_11_R1", "1_12_R1", "1_13_R2",
	 "1_14_R1", "1_15_R1", "1_16_R3"
)

fun includeAdapts(vararg adapts: String) {
	for (adaptPath in adapts) {
		include(":adapt:v$adaptPath")
	}
}