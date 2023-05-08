# *QuickPass* - Simple Password Manager

### A simple password manager able to generate and store log-in info for different users

With the recent rapid growth of the tech industry, new useful services requiring sign-ons are appearing at a rapid pace.
However, this rapid growth also comes with rising concerns about cybersecurity, meaning it is often not as simple as
making an easy-to-remember password or even worse using the same one for all services.<br>

That's where QuickPass comes in, a simple password manager able to generate, edit, remove and store passwords for you
and your loved ones in one place. This service is designed for CS students with too many websites to keep track of like
me, people who want less hassle in their daily lives or just folks who want to have 50 different 50-bit long passwords.

User Stories:

- As a user, I want to be able to add a password to my list with an associated title
- As a user, I want to be able to generate passwords with certain criteria for use
- As a user, I want to be able to have multiple master accounts, for use on a shared PC(e.g. family PC)
- As a user, I want to be able to manually edit passwords and their associated titles
- As a user, I want to be able to search through my password list
- As a user, I want to be able to delete passwords/entire master accounts
- As a user, I want to be able to save/load passwords from a JSON file

# Phase 4: Task 2

Represents a user who opens the app to add 2 new passwords onto 
their old saved list(load),
deletes an old unused one and
checks to make sure the changes went through using the display button. The user saves once done
and displays one last time check before closure. (Note that
currently, the load operation also logs as adding passwords
since it uses the same add method. This is left as
is because it is arguably a useful feature and the benefits don't
justify going back and potentially causing other issues, at least not for
a school project)


- Fri Dec 02 18:49:17 PST 2022
- Password (name) added 
- above repeated * # of passwords to load
- Fri Dec 02 18:49:17 PST 2022
- Password loaded
- Fri Dec 02 18:49:22 PST 2022
- Password (name) added.
- Fri Dec 02 18:49:24 PST 2022
- Password (name) added.
- Fri Dec 02 18:49:29 PST 2022
- Password (name) removed.
- Fri Dec 02 18:49:32 PST 2022
- Passwords displayed
- Fri Dec 02 18:49:33 PST 2022
- Passwords saved
- Fri Dec 02 18:49:38 PST 2022
- Passwords displayed

# Phase 4: Task 3

Some refactoring that I would do to improve my design would be:

- Improving abstraction, I struggled a lot with method length and code
  redundancy throughout the project, however as the course went along my mastery
  of both java and code design as a whole improved. I feel like if I were to go back now
  (and I have for a good chunk of the code),
  I could still improve both the readability and efficiency of my old code quite a bit
  (e.g. abstracting the creation of my Jframe more, specifically with adding elements)


- Better code cohesion, some methods are blatantly out of place in my program,
  for example, the display passwords method really shouldn't be in main class and some other selection methods should be
  shortened so main class is only concerned with user input and not the specific implementation


- Error checking, I started with no intention of doing error checking so now it's kind of hard
  to have to go back and refactor all of my code to make new exceptions, wrap in try-catch
  and to handle errors properly. But the benefits are obvious. Next time I should just start
  with that in mind, seeing how error checking
  would also help with allowing users to cancel operations more gracefully instead of my current very hard-to-read and
  unnecessary complex nesting of ifs.


- Finally, just better coding in general, I feel like now that I'm more familiar with java and design
  principles there are probably a lot of methods that I can improve the efficiency of(different from
  abstraction, this is pure code optimization) or just do some things differently to
  make it faster and better in general