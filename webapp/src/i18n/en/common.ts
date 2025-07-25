import { ExceptionCode } from "@/@types/errorCode";
import { LocalState } from "@/@types/localState";
import { Role } from "@/store/userStore";

const error = {
  baseTitle: "Error occurred",
  baseDescription: "Something went wrong...",
  userBlocked: "Your account is blocked",
  internalServerErrorDescription:
    "Oops! Something went wrong on our end. Please try again later.",
};

const loginPage = {
  forgotPassword: "Forgot password?",
  loginButton: "Sign in",
  login: "Login",
  password: "Password",
  loginHeader: "Sign in",
  register: "Sign up",
  loginRequired: "Login is required",
  passwordRequired: "Password is required",
  loginError: "Login error",
  invalidCredentials: "Invalid credentials",
  loginNotAllowed: "Login is blocked, check email",
  tryAgain: "Try again",
  codeLengthMessage: "Two-factor authentication code must be 8 digits",
  codeDescription: "Enter two-factor authentication code",
  changeLanguage: "Language",
  submit: "Submit",
  backToLoginForm: "Back to login form",
  tokenError: {
    title: "Incorrect data",
    description: "Provided incorrect token",
  },
  googleLoginButton: "Sign in with Google",
  inactiveAccount: "Your account is inactive, check you e-mail to continue",
};

const userDetailsPage = {
  firstName: "First name",
  lastName: "Last name",
  login: "Login",
  email: "Email",
  language: "Language",
  lastSuccessfulLogin: "Last successful login",
  lastFailedLogin: "Last failed login",
  timezone: "Timezone",
  blocked: "Blocked",
  verified: "Verified",
  active: "Active",
  actions: "Actions",

  role: {
    title: "Roles",
    add: {
      tenant: "Add tenant role",
      owner: "Add owner role",
      administrator: "Add administrator role",
    },
    remove: {
      tenant: "Remove tenant role",
      owner: "Remove owner role",
      administrator: "Remove administrator role",
    },
    added: {
      tenant: "Added tenant role",
      owner: "Added owner role",
      administrator: "Added administrator role",
    },
    removed: {
      tenant: "Removed tenant role",
      owner: "Removed owner role",
      administrator: "Removed administrator role",
    },
  },
  changeEmail: "Change email address",
  updateEmailAddress: "Update email address",
  updateEmailAddressTitle:
    "Are you sure you want to update this user's email address?",
  updateEmailAddressDescription:
    "A link to change the email address will be sent to the given email address",
  addLocal: "Add local",
};

const updateEmailPage = {
  emailNotValid: "Email is not valid",
  email: "Email*",
  updateEmailButton: "Update email",
  updateEmailSuccess: "Email has been updated",
  updateEmailError: "Error while updating email",
  updateEmailTitle: "Enter your password",
  success: "Success",
  error: "Error",
  password: "Password*",
  confirmPassword: "Repeat password*",
};

const registerPage = {
  firstNameRequired: "First name is required",
  lastNameRequired: "Last name is required",
  emailRequired: "Email is required",
  loginRequired: "Login is required",
  passwordRequired: "Password must be at least 8 characters long",
  passwordMatch: "Passwords must match",
  registerHeader: "Sign up",
  firstName: "First name*",
  lastName: "Last name*",
  email: "Email*",
  login: "Login*",
  password: "Password*",
  confirmPassword: "Confirm password*",
  registerButton: "Sign up",
  registerSuccess:
    "Link to confirm registration has been sent to the provided email address.",
  registerError: "Error while registering",
  tryAgain: "Try again",
  registerErrorIdenticalFields: "Login and email must be different",
};

const resetPasswordForm = {
  description:
    "Enter your email address and we will send you a link to reset your password",
  email: "Email*",
  emailRequired: "Email is required",
  resetPassword: "Reset password",
  loginButton: "Back to login form",
  resetUserPasswordToastTitleSuccess: "Operation successful",
  resetUserPasswordToastDescriptionSuccess:
    "Link has been sent to the provided email address",
  resetUserPasswordToastTitleFail: "Operation failed",
  resetUserPasswordToastDescriptionNotFound:
    "User with provided email address not found",
  resetUserPasswordToastDescriptionFail: "Something went wrong...",
};

const resetPasswordPage = {
  header: "Reset password",
  password: "Password*",
  confirmPassword: "Confirm password*",
  confirmButton: "Reset password",
  homeButton: "Back to home",
  changePasswordToastTitleSuccess: "Operation successful",
  changePasswordToastDescriptionSuccess: "Password has been changed",
  changePasswordToastTitleFail: "Operation failed",
  changePasswordToastDescriptionFail: "Something went wrong...",
};

const changePasswordForm = {
  oldPassword: "Old password*",
  newPassword: "New password*",
  confirmPassword: "Confirm new password*",
  submit: "Change",
  success: "Success",
  successDescription: "Password has been changed",
  errorTitle: "Error",
  errorDescriptionNotFound: "User not found",
  errorDescriptionBadRequest: "Incorrect password",
  errorDescriptionConflict:
    "The password must be unique compared to previous ones",
  alertDialogTitle: "Change password",
  errorDescriptionTokenNotValid:
    "The provided token is invalid, email resend required.",
  alertDialogDescription: "Are you sure you want to change your password?",
  alertDialogCancel: "No",
  alertDialogAction: "Yes",
};

const userListPage = {
  firstName: "First name",
  lastName: "Last name",
  login: "Login",
  email: "Email",
  actions: "Actions",
  viewDetails: "Details",
  resetUserPasswordAction: "Reset password",
  resetUserPasswordTitle: "Reset user password",
  resetUserPasswordDescription: "Are you sure you want to reset user password ",
  resetUserPasswordToastTitleSuccess: "Operation successful",
  resetUserPasswordToastDescriptionSuccess:
    "Password reset link has been sent to the user email address",
  resetUserPasswordToastTitleFail: "Operation failed",
  resetUserPasswordToastDescriptionNotFound: "Not found given user.",
  resetUserPasswordToastDescriptionForbidden:
    "User is not verified or blocked.",
  resetUserPasswordToastDescriptionFail: "Something went wrong...",
  resetUserEmailAction: "Update email",
  resetUserEmailSuccess:
    "An email for changing current address has been sent to the given email address",
  resetUserEmailError: "Error while initializing email change",
  resetUserEmailTitle: "Are you sure you want to update user email address?",
  resetUserEmailDescription:
    "A link to change the email address will be sent to the user's email address",
  updateEmailAddress: "Update email address",
  breadcrumbsUserListPage: "Users",
};

const block = {
  blockUserAction: "Block",
  unblockUserAction: "Unblock",
  toast: {
    title: {
      success: "Operation success",
      fail: "Operation failed",
    },

    description: {
      blockSuccess: "User successfully blocked.",
      unblockSuccess: "User successfully unblocked.",
      notFound: "Users not found.",
      alreadyBlocked: "User already blocked.",
      alreadyUnblocked: "User already unblocked.",
      fail: "Something went wrong...",
    },
  },
};

const userFilter = {
  yes: "Yes",
  no: "No",
  both: "Both",
  verified: "Verified",
  blocked: "Blocked",
  login: "Login",
  email: "Email",
  submit: "Filter",
  role: "Role",
  all: "All",
  tenant: "Tenant",
  owner: "Owner",
  administrator: "Administrator",
  firstName: "First Name",
  lastName: "Last Name",
  clear: "Clear",
};

const pageChanger = {
  numberOfElements: "Number of elements",
  page: "Page",
  of: "of",
};

const common = {
  yes: "yes",
  no: "no",
  update: "Update",
  confirmDialogTitle: "Are you sure?",
  backToHomePage: "Back to home page",
};

const navLinks = {
  account: "My account",
  signOut: "Sign out",
  users: "Users",
  notApprovedActions: "Unapproved actions",
  roles: "Change access level",
  locals: "Locals",
  currentRents: "Current rents",
  archivalRents: "Archival rents",
  addLocal: "Add local",
  applications: "Rent applications",
  menu: "Menu",
} satisfies {
  [key in string]: string;
};

const userDataPage = {
  firstNameNotEmpty: "First name cannot be empty",
  lastNameNotEmpty: "Last name cannot be empty",
  firstName: "First name*",
  lastName: "Last name*",
  language: "Language*",
  error: "Error",
  success: "Data updated",
  updateUserData: "Update",
  emailNotValid: "Email is not valid",
  email: "Email*",
  emailNotEmpty: "Email cannot be empty",
  emailTooLong: "Email is too long",
  confirmDialogDescription: "Are you sure you want to change personal data?",
  timeZone: "Time zone",
};

const updateDataForm = {
  error: "Error during updating data",
  success: "Data updated",
  precondinationFailed: "You are not working on the latest data",
  triggerButton: "Update data",
  title: "Update user data",
  firstName: "First name",
  lastName: "Last name",
  language: "Language",
  updateButton: "Update",
  updateUserData: "Update user data",
};

const mePage = {
  accountInfo: "User data",
  updateEmailAddress: "Update email address",
  updateEmailAddressTitle:
    "Are you sure you want to update your email address?",
  updateEmailAddressDescription:
    "A link to change the email address will be sent to the user's email address",
  title: "My account",
  basicInformation: "Basic information",
  firstNameLabel: "First name",
  lastNamelabel: "Last name",
  emailLabel: "Email",
  lastSuccessfullLoginDateLabel: "Last successful Login Date",
  lastSuccessfillLoginIPLabel: "Last successful Login IP",
  lastFailedfullLoginDateLabel: "Last failed Login Date",
  lastFailedfillLoginIPLabel: "Last failed Login IP",
  updateData: "Update your data",
  changeEmail: "Change your email address",
  changePassword: "Change your password",
  changeEmailDescription: "Click here to send email with link to change email.",
  emailInput: "Email*",
  timezone: "Timezone",
};

const addLocalPage = {
  title: "Add new local",
  name: "Name",
  description: "Description",
  size: "Size",
  number: "Number",
  street: "Street name",
  city: "City",
  zip: "Postal code",
  country: "Country",
  marginFee: "Margin fee",
  rentalFee: "Rental fee",
  formSubmit: "Send form",
  wrong: {
    name: "Wrong local name",
    description: "Wrong local description",
    size: "Wrong local size",
    number: "Wrong local number",
    street: "Wrong street name",
    city: "Wrong city",
    zip: "Wrong postal code",
    country: "Wrong country",
    marginFee: "Wrong margin fee",
    rentalFee: "Wrong rental fee",
  },
  error: "Failed to add local",
  successTitle: "Local added",
  successDescription: "Local successfully added",
};

const registerSuccessPage = {
  title: "Thanks for creating an account",
  description:
    "We send you an email with verification link. Use this link to verify your account. Until you perform this action you won't be able to login.",
  loginButton: "Go back to login",
};

const notApprovedActionsPage = {
  title: "Not approved actions",
  roleRequests: "Requests for role",
  locals: "Locals",
  emptyRoleRequests: "No avaliable role requsts",
  emptyUnapprovedLocals: "No unapproved locals",
  actions: "Actions",
  confirm: "Confirm",
  reject: "Reject",
  confirmDialog: {
    title: "Are you sure?",
    confirmDescription: "Are you sure you want to confirm this local?",
    rejectDescription: "Are you sure you want to reject this local?",
  },
  unapprovedLocals: {
    name: "Name",
    address: "Address",
    owner: "Owner",
    details: "Details",
  },
  approve: "Approve",
  show: "Show",
  roleRequestApproveSuccess: "Role request approved",
  roleRequestRejectSuccess: "Role request rejected",
};

const validation = {
  characters: "character(s)",
  minLength: "Field must contain at least",
  maxLength: "Field must contain at most",
};

const roles = {
  administrator: "Administrator",
  tenant: "Tenant",
  owner: "Owner",
} satisfies {
  [key in Role]: string;
};

const homePage = {
  manageProperties: "Manage your properties with ease",
  signIn: "Sign in",
  signUp: "Create account",
  or: "or",
};

const sessionExpiredDialog = {
  title: "Session will expire soon!",
  description:
    "Your session will expire in less than 5 minutes. Do you want to extend it?",
  signOut: "Logout",
};

const errors = {
  optimisticLock: "You are not working on the latest data",
  registrationError: "Error while registering",
  identicalLoginOrEmail: "Account with this login or email already exists",
  invalidData: "Invalid data",
  invalidLoginData: "User with provided login and password not found",
  invalidPassword: "User with provided login and password not found",
  loginNotMatchToOTP: "One-time password does not match",
  passwordRepetition: "You must provide password other than the previous one",
  invalidRefreshToken: "Error while refreshing session, login required",
  signInBlocked:
    "You give incorrect data too many times, your account is blocked, check email",
  userAlreadyBlocked: "User already blocked",
  userAlreadyUnblocked: "User already unblocked",
  userBlocked: "You are blocked",
  userInactive: "You have been inactive for too long, check your email",
  userNotVerified: "You are not verified, chceck your email",
  verificationTokenExpired: "Code expired",
  verificationTokenUsed: "Invalid code",
  administratorOwnRoleRemoval: "You cannot remove your own administrator role",
  administratorOwnBlock: "You cannot block yourself",
  notFound: "Not found",
  userNotFound: "User not found",
  themeNotFound: "Theme not found",
  somethingWentWrong: "Something went wrong...",
  accessDenied: "Access denied",
  jwtTokenInvalid: "Session expired",
  validationError: "Validation error",
  identicalEmail: "User with given email address already exists",
  internalServerError:
    "Something went wrong on our end. Please try again later.",
  accessLevelAssigned: "Access level aleady assigned",
  accessLevelTaken: "Access level already taken",
  undefined: "Unexpected error occurred",
  localNotFound: "Local not found",
  localNotActive: "Local must be active for this operation",
  localNotInactive: "Local must be inactive for this operation",
  localNotUnapproved: "Local must be unapproved for this operation",
  wrongEndDate:
    "End date must be in the future, Sunday, after start date and different from the current end date",
  rentNotFound: "Rent not found",
  invalidLocalStateArchive: "Local must be in Without owner state to archive",
  addressAlreadyAssigned: "Address asigned to another local",
  rentEnded: "Rent has already ended",
  dateParsingError: "Date parsing error",
  variableFeeAlreadyExists: "Variable fee already exists",
  paymentAlreadyExists: "Payment for current week already exists",
  roleRequestAlreadyExists: "Role request already exists",
  rollback: "Rollback",
  transaction: "Transaction",
  unexpectedRollback: "Unexpected rollback",
  userAlreadyHasRole: "User already has this role",
  applicationExists: "Application for this local already exists",
  applicationNotFound: "Application not found",
  roleRequestNotFound: "Role request not found",
  imageNotFound: "Image not found",
} satisfies {
  [key in ExceptionCode]: string;
};

const notFoundPage = {
  title: "Not Found",
  description: "The page you are looking for does not exist.",
  login: "Login",
  home: "Home",
};

const ownerLocals = {
  show: "Show",
  noLocalsFound: "Currently you have no locals",
  addFirstLocal: "Add first local",
  title: "Main page",
  locals: "Locals",
  localState: "Local state",
  all: "All",
  noLocalsFoundForThisState: "Currently you have no locals in this state",
  report: "Report",
};

const allLocals = {
  title: "All locals",
  show: "Show",
  localOwner: "Owner: ",
  noLocalsFound: "Currently there are no locals",
  noOwner: "No owner",
  localState: "Local state",
  all: "All",
  noLocalsFoundWithGivenParameters:
    "Currently there are no locals with given parameters",
  login: "Owner login",
};

const localState = {
  UNAPPROVED: "Unapproved",
  ARCHIVED: "Archived",
  ACTIVE: "Active",
  INACTIVE: "Inactive",
  WITHOUT_OWNER: "Without owner",
  RENTED: "Rented",
} satisfies {
  [key in LocalState]: string;
};

const tenantRents = {
  startDate: "Start date",
  endDate: "End date",
  fixedFee: "Fixed fee",
  balance: "Balance",
  localSize: "Local size",
  owner: "Owner",
  name: "Name",
  email: "Email",
  login: "Login",
  noData: "Currently you have no rents",
  details: "Details",
  rentsNotFund: "No rents found",
};

const currentOwnerRents = {
  title: "Owner",
  rents: "Current rents",
  startDate: "Start date",
  endDate: "End date",
  balance: "Balance",
  tenant: "Tenant",
  name: "Name",
  email: "Email",
  noRentsFound: "Currently your local has no tenants",
  rentDetails: "Rent details",
  archivalRents: "Archival rents",
  rentsNotFound: "No rents found",
};

const leaveLocal = {
  successTitle: "Success",
  successDescription: "You have left the local",
  errorTitle: "Error",
  buttonText: "Leave local",
  dialogTitle: "Are you sure you want to continue?",
  dialogDescription: "You cannot undo this operation.",
};

const currentTenantRents = {
  startDate: "Start date",
  endDate: "End date",
  fixedFee: "Fixed fee",
  balance: "Balance",
  size: "Local size",
  owner: "Owner",
  name: "Name",
  email: "Email",
  login: "Login",
};

const changeEndDate = {
  successTitle: "Success",
  successDescription: "End date has been changed",
  errorTitle: "Error",
  buttonText: "Change end date",
  newDateRequired: "New date is required",
  dialogTitle: "Change rent's end date",
  dialogDescription: "Choose a new end date for the rent.",
  formLabel: "New end date",
  formDescription: "End date must be in the future and it must be Sunday.",
  spanText: "Pick a date",
  saveChanges: "Save changes",
};
const roleRequestDialog = {
  description:
    "You can submit a request for an owner role. Once we receive your request, we will review your account to determine your eligibility. If the role is assigned to you, you will receive an email notification.",
  requestOwnerRole: "Request Owner role",
  requestOwnerRoleDescription:
    "Click this button to submit a request for an owner role.",
  alreadyPlacedRequest: "You already placed a request for role at: ",
  howDoesItWork: "How does this work?",
  requestRoleButton: "Request role",
  succesTitle: "Role request created",
  succesDescription: "We will review your request and get back to you soon",
};

const localDetails = {
  name: "Name",
  error: "Error while fetching local details",
  firstName: "First name",
  lastName: "Last name",
  owner: "Owner",
  login: "Login",
  email: "Email",
  address: "Address",
  size: "Size",
  rentalFee: "Rental fee",
  marginFee: "Margin fee",
  nextRentalFee: "Next rental fee",
  nextMarginlFee: "Next margin fee",
  price: "Price",
  totalPrice: "Total",
  description: "Description",
  country: "Country",
  city: "City",
  street: "Street",
  number: "Number",
  zipCode: "Zip code",
  showOwnerDetails: "Show owner details",
  localInformation: "Local information",
  ownerInformation: "Owner information",
  addressInformation: "Address information",
  basicInformation: "Basic information",
  updateData: "Update data",
  changeAddress: "Change address",
  archiveLocal: "Archive local",
  approveLocal: "Approve local",
  archiveLocalDescription: "Are you sure you want to archive this local?",
  close: "Close",
  archiveError: "Archive error",
  archiveSuccess: "Local has been archived",
  state: "State",
};

const changeAddressForm = {
  cityValidation: "City must be between 1 and 100 characters",
  countryValidation: "Country must be between 1 and 100 characters",
  streetValidation: "Street must be between 1 and 100 characters",
  numberValidation: "Number must be between 1 and 10 characters",
  zipCodeValidation: "Zip code must be in format 12-345",
  country: "Country*",
  city: "City*",
  street: "Street*",
  number: "Number*",
  zipCode: "Zip code*",
  confirmDialogDescription: "Are you sure you want to change address?",
  addressUpdateSuccess: "Address has been updated",
};

const ownLocalDetails = {
  error: "Error while fetching local details",
  address: "Address",
  size: "Size",
  rentalFee: "Rental fee",
  marginFee: "Margin fee",
  nextRentalFee: "Next rental fee",
  nextMarginFee: "Next margin fee",
  description: "Description",
  country: "Country",
  city: "City",
  street: "Street",
  number: "Number",
  zipCode: "Zip code",
  localInformation: "Local information",
  ownerInformation: "Owner information",
  addressInformation: "Address information",
  basicInformation: "Basic information",
  updateData: "Update data",
  changeFixedFee: "Change fixed fee",
  changeFixedFeeDescription:
    "Changes to the fixed fees will be effective from the next billing period.",
  leaveLocal: "Leave local",
  leaveLocalDescription:
    "This operation cannot be undone. You can leave local only in Inactive state.",
  showApplications: "Show applications",
  state: "Local state",
  uploadImage: "Upload image",
};

const activeLocals = {
  error: "Error while fetching active locals",
  size: "Size",
  city: "City",
  show: "Details",
  noLocalsFound: "No locals to show",
};

const localFilter = {
  city: "City name",
  cityPlaceholder: "Input name",
  minSize: "Minimal size",
  minSizePlaceholder: "0.0",
  maxSize: "Maximal size",
  maxSizePlaceholder: "0.0",
  submit: "Filter",
  clear: "Clear filters",
};

const localApplications = {
  errorTitle: "Error loading applications",
  showApplications: "Show applications",
  applicant: "Applicant",
  createdAt: "Application date",
  accept: "Accept",
  reject: "Reject",
  noApplications: "No applications available",
  email: "Email",
  rejectTitle: "Reject application",
  rejectDescription: "Are you sure you want to reject this application?",
  rejectSuccess: "Application rejected",
  acceptTitle: "Confirm Application Acceptance",
  acceptDescription:
    "You are about to accept this application. Upon acceptance, a rental agreement will be created with an end date. Please ensure all details are correct before proceeding",
  acceptFooter: "After accepting the application local will be rented",
  acceptSuccess: "Application accepted",
  endDateNeeded: "End date is required",
};

const activeLocalDetails = {
  firstName: "First name",
  size: "Size",
  price: "Price",
  owner: "Owner",
  city: "City",
  description: "Description",
  localInformation: "Local information",
  ownerInformation: "Owner information",
  apply: "Apply",
  applicationTitle: "Application",
  applicationDescription: "Are you sure you want to apply for this local?",
  applicationExistsDescription: "You apply for this local: ",
  applicationCreated: "Application created",
};

const updateLocalPage = {
  state: "State",
  changeState: "Change state to",
  states: {
    withoutOwner: "Without owner",
    unapproved: "Unapproved",
    archived: "Archived",
    active: "Active",
    inactive: "Inactive",
    rented: "Rented",
    unknown: "Unknown",
  },
  updateData: "Update local data",
  name: "Local name",
  description: "Local description",
  size: "Size",
  reset: "Reset",
  submit: "Submit",
  wrong: {
    name: "Wrong name",
    description: "Wrong description",
    size: "Wrong size",
    state: "Wrong state",
  },
  successTitle: "Success",
  successDescription: "Local updated",
  errorTitle: "Error",
  confirmDialogDescription: "Are you sure you want to change local?",
};

const updateOwnLocalFixedFeeForm = {
  rentalFeeNotEmpty: "Rental fee cannot be empty.",
  rentalFeeNotValid:
    "Rental fee must be a valid monetary amount with up to 2 decimal places.",
  marginFeeNotEmpty: "Margin fee cannot be empty.",
  marginFeeNotValid:
    "Margin fee must be a valid monetary amount with up to 2 decimal places.",
  rentalFeeInput: "Enter Rental Fee",
  marginFeeInput: "Enter Margin Fee",
  updateFixedFee: "Update Fixed Fee",
  updateFixedFeeTitle: "Confirm Update",
  updateFixedFeeDescription: "Are you sure you want to update the fixed fee?",
  rentalFeeTooLarge: "Rental fee cannot exceed 1,000,000.",
  marginFeeTooLarge: "Margin fee cannot exceed 1,000,000.",
  rentalFeeCannotBeZero: "Rental fee cannot be zero.",
  marginFeeCannotBeZero: "Margin fee cannot be zero.",
};

const changeFixedFee = {
  successTitle: "Success",
  successDescription: "Fixed fee has been changed",
  errorTitle: "Error",
};

const ownerRentDetails = {
  error: "Error while fetching rent details",
  ownerMainPage: "Owner",
  adminMainPage: "Administrator",
  rents: "Current rents",
  rent: "Rent of local ",
  rentDetails: "Rent details",
  rentInfo: "Rent information",
  tenantInfo: "Tenant information",
  addressInfo: "Address information",
  payments: "Payments",
  fixedFees: "Fixed fees",
  noPayments: "No payments",
  variableFees: "Variable fees",
  localInfo: "Local information",
  startDate: "Start date",
  endDate: "End date",
  name: "Name",
  email: "Email",
  login: "Login",
  address: "Address",
  date: "Date",
  amount: "Amount",
  selectStart: "Select start date",
  selectEnd: "Select end date",
  number: "No.",
  localName: "Name",
  balance: "Balance",
  pickDate: "Pick a date",
  margin: "Margin Fee",
  rental: "Rental Fee",
  summary: "Total",
  showLocalDetails: "Show local details",
  archivalRents: "Archival rents",
  rentNotFound: "Rent not found",
  marginFee: "Margin fee",
  rentalFee: "Rental fee",
};

const tenantApplications = {
  createdAt: "Created at",
  linkToLocal: "Link to local",
  applicationsNotFund: "No applications found",
  deleteApplication: "Remove application",
  deleteApplicationDescription: "Are you sure you want to delete application?",
  aplicationDeleted: "Application deleted",
};

const breadcrumbs = {
  admin: "Administrator",
  tenant: "Tenant",
  locals: "Locals",
  local: "Local",
  report: "Report",
  currentRents: "Current rents",
  archivalRents: "Archival rents",
};

const createVariableFeeDialog = {
  title: "Add Variable fee",
  amount: "Amount*",
  amountMustBePositive: "Amount must be positive",
  success: "Variable fee created",
};

const createPaymentDialog = {
  success: "Payment created",
  title: "Create payment",
  amount: "Amount*",
  amountMustBePositive: "Amount must be positive",
  amountMustBeLessThanOrEqualToTenThousand: "Amount must be less than 10,000",
  amountMustHaveAtMostTwoFractionalDigits:
    "Amount must have at most two fractional digits",
};

const rentDetailsPage = {
  rentDetails: "Rent details",
  localDetails: "Local details",
  ownerDetails: "Owner details",
  labels: {
    startDate: "Start date",
    endDate: "End date",
    balance: "Balance",
    localName: "Local name",
    address: "Address",
    fixedFee: "Fixed fee",
    name: "Name",
    email: "Email",
  },
  details: "Details",
  payments: "Payments",
  fixedFees: "Fixed fees",
  variableFees: "Variable fees",
};

const localReport = {
  marginFee: "Margin fee",
  rentalFee: "Rental fee",
  fixedFee: "Fixed fee",
  variableFee: "Variable fee",
  payment: "Payment",
  summary: "Summary",
  totalPayments: "Total payments",
  totalVariableFees: "Total variable fees",
  totalFixedFees: "Total fixed fees",
  totalMarginFees: "Profit from margin fees",
  totalRentalFees: "Rental fees",
  localSummary: "Local summary",
  longestRentDays: "Longest rent in days",
  shortestRentDays: "Shortest rent in days",
  rentCount: "Number of rents",
  summaryPieChart: "Summary of fees and profits",
  summaryBarChart: "Payments and fees weekly summary",
  numberOfWeeks: "Number of weeks",
};

const uploadImage = {
  uploadImage: "Upload image",
  uploadImageDescription:
    "Upload an image of your local to attract more customers.",
  uploadedFileTooLarge: "Uploaded file is too large. Max size is 1MB",
  uploadedFileNotImage: "Only .png and .jpeg files are allowed",
  chooseFile: "Choose file",
  upload: "Upload",
  delete: "Delete",
  deleteSuccessTitle: "Image deleted",
  deleteSuccessDescription: "Image has been deleted successfully",
  deleteErrorTitle: "Error. Image not deleted",
  uploadImageSuccess: "Image has been uploaded successfully",
  uploadImageError: "Error while uploading image",
};

const landingPage = {
  title: "Landlord Kingdom",
  subtitle: "Your Property Management Solution",
  description:
    "Manage your properties with ease. Landlord Kingdom is a platform that allows property owners to manage their locals, rents, and payments in one place.",
  viewLocals: "Check available locals",
  features: {
    title: "Why Choose Landlord Kingdom?",
    propertyManagement: {
      title: "Property Management",
      description:
        "Easily manage all your properties in one centralized platform",
    },
    rentTracking: {
      title: "Rent Tracking",
      description:
        "Track rent payments and manage tenant relationships effortlessly",
    },
    reporting: {
      title: "Financial Reporting",
      description:
        "Generate detailed reports to track your property income and expenses",
    },
  },
  cta: {
    getStarted: "Get Started Today",
    learnMore: "Learn More",
    signUp: "Sign Up Now",
    login: "Already have an account? Sign in",
  },
};

const aboutPage = {
  title: "About Us",
  description:
    "Welcome to Landlord Kingdom, your comprehensive property management solution. We are dedicated to simplifying the rental process for both property owners and tenants, providing a seamless platform that brings efficiency and transparency to property management.",
  mission: {
    title: "Our Mission",
    description:
      "To revolutionize property management by providing innovative tools that make rental processes simpler, more efficient, and transparent for everyone involved. We strive to bridge the gap between landlords and tenants through technology.",
  },
  vision: {
    title: "Our Vision",
    description:
      "To become the leading platform for property management, creating a world where rental processes are seamless, relationships are built on trust, and property management is accessible to everyone.",
  },
  values: {
    title: "Our Core Values",
    transparency:
      "Transparency - We believe in open communication and clear processes for all parties involved.",
    reliability:
      "Reliability - Our platform provides dependable service you can count on, day after day.",
    innovation:
      "Innovation - We continuously improve our platform with cutting-edge technology and user feedback.",
    customerService:
      "Customer Service - We are committed to providing exceptional support and assistance to our users.",
  },
};

const contactPage = {
  title: "Contact",
  description:
    "Do you have questions, suggestions, or need more information? Contact us - we'll be happy to help!",
  emailTitle: "E-mail",
  emailAddress: "contact@yourdomain.com",
  phoneTitle: "Phone",
  phoneNumber: "+48 123 456 789",
  phoneHours: "(Mon.-Fri. 9:00-17:00)",
  addressTitle: "Correspondence address",
  companyName: "Your Company Ltd.",
  street: "Example Street 1",
  postalCode: "00-000 Warsaw",
};

const cookiesPage = {
  title: "Cookie Policy",
  whatAreCookies: {
    title: "What are cookies?",
    description:
      "Cookies are small text files stored on your device (computer, tablet, smartphone) when using our website. They enable, among other things, proper functioning of the service, traffic analysis, and content customization to your preferences.",
  },
  typesOfCookies: {
    title: "What cookies do we use?",
    description: "On our website we use the following types of cookies:",
    necessary: {
      title: "Necessary",
      description:
        "enable proper functioning of the website, e.g., login, navigation, or saving privacy settings. They do not require user consent.",
    },
    analytical: {
      title: "Analytical",
      description:
        "allow collecting anonymous statistical data about how the website is used, e.g., through Google Analytics.",
    },
    functional: {
      title: "Functional",
      description:
        "remember your preferences (e.g., language, location) to improve user experience.",
    },
  },
  manageCookies: {
    title: "How to manage cookies?",
    description:
      "During your first visit to our website, a banner with information about cookies appears. You have the option to:",
    acceptAll: "accept all cookies,",
    rejectOptional: "reject them except for necessary ones",
    changeSettings:
      "You can change your cookie settings at any time by clicking",
    here: "here",
    orBrowser: "or by changing settings in your browser.",
  },
  policyChanges: {
    title: "Changes to cookie policy",
    description:
      "We reserve the right to make changes to this policy. The current version will always be available on this page.",
  },
  contact: {
    title: "Contact",
    description:
      "For questions regarding the cookie policy, please contact us at:",
    email: "[contact email address]",
  },
};

const termsPage = {
  title: "Terms of Service",
  section1: {
    title: "§1 General provisions",
    paragraph1:
      "These terms of service define the rules for using the [YourName.com] website.",
    paragraph2:
      "Every user of the service accepts the terms set out in these regulations.",
    paragraph3:
      "The owner of the service is [Company name / individual], with headquarters at [address], VAT: [number].",
  },
  section2: {
    title: "§2 Usage rules",
    point1:
      "The service is available to all users without the need for registration.",
    point2:
      "The user undertakes to use the service in accordance with applicable law and good practices.",
    point3: {
      main: "It is forbidden to:",
      sub1: "undertake actions that may disrupt the operation of the service,",
      sub2: "publish content of an illegal, offensive, or spam nature,",
      sub3: "use content from the site without the owner's consent.",
    },
  },
  section3: {
    title: "§3 Copyright",
    paragraph1:
      "All content, graphics, photos, and materials published on the site are protected by copyright.",
    paragraph2:
      "Copying, distributing, or using them for commercial purposes without the owner's consent is prohibited.",
  },
  section4: {
    title: "§4 Liability",
    paragraph1:
      "The service owner makes every effort to ensure that the information contained on the site is current and correct, but is not responsible for its use by users.",
    paragraph2:
      "The service may contain links to external sites - the owner is not responsible for their content.",
  },
  section5: {
    title: "§5 Changes to terms",
    paragraph1:
      "The owner reserves the right to make changes to the regulations at any time.",
    paragraph2:
      "Changes take effect from the date of their publication on the site.",
  },
  section6: {
    title: "§6 Contact",
    paragraph:
      "For questions regarding the regulations, please contact: [contact email address]",
  },
};

export default {
  aboutPage,
  contactPage,
  cookiesPage,
  termsPage,
  uploadImage,
  localReport,
  rentDetailsPage,
  createPaymentDialog,
  createVariableFeeDialog,
  tenantApplications,
  changeAddressForm,
  activeLocalDetails,
  landingPage,
  ownerRentDetails,
  breadcrumbs,
  localDetails,
  ownLocalDetails,
  updateOwnLocalFixedFeeForm,
  changeFixedFee,
  changeEndDate,
  currentOwnerRents,
  currentTenantRents,
  tenantRents,
  leaveLocal,
  roleRequestDialog,
  allLocals,
  localState,
  ownerLocals,
  updateLocalPage,
  activeLocals,
  notFoundPage,
  sessionExpiredDialog,
  error,
  errors,
  homePage,
  common,
  roles,
  registerSuccessPage,
  navLinks,
  loginPage,
  registerPage,
  resetPasswordForm,
  resetPasswordPage,
  changePasswordForm,
  userListPage,
  userFilter,
  block,
  userDetailsPage,
  updateDataForm,
  mePage,
  addLocalPage,
  localApplications,
  userDataPage,
  updateEmailPage,
  validation,
  pageChanger,
  localFilter,
  notApprovedActionsPage,
  light: "Light",
  dark: "Dark",
  reactivationSuccess: "Your account has been reactivated. You can now login.",
  success: "Success",
  sessionExpired: "Session expired",
  sessionExpiredDescription: "Session expired, please login again",
  footer: "Landlord Kingdom",
  logoPlaceholder: "Landlord Kingdom",
  confirm: "Confirm",
  cancel: "Cancel",
  currency: "PLN",
} as const;
