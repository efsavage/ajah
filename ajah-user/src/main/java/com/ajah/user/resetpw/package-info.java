/**
Password resets token creation/management.

Password resets are done via a persisted token mechanism for a few reasons:

<ul>
<li>Logging - We'd like to be able to track reset requests for measuring such things as:
	<ul>
		<li>Feature usage and success.</li>
		<li>Intrusion attempt metrics.</li>
	</ul></li>
<li>Security - This is basically a one-time pad, so no cracking short of random guesses is possible.</li>
<li>Intrusion recovery - In the event an intrustion is suspected, this attack vector can be eliminated simply by invalidating or deleting all issued tokens.</li>
</ul> 

@author Eric F. Savage
@version 1.0
 */
package com.ajah.user.resetpw;