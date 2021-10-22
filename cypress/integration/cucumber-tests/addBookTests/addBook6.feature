Feature: Add Book 6

    Scenario: Submit button disabled and user remains on Add book page upon selection
        Given User is on the Add book page
        And Submit button is disabled
        When User selects the Submit button
        Then User remains on the Add book page