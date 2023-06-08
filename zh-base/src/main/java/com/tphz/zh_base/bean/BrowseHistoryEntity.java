package com.tphz.zh_base.bean;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import java.util.List;

public class BrowseHistoryEntity implements Parcelable {


    private List<String> taskNameList;
    private int integralNum;
    private List<TaskProblemListDTO> taskProblemList;


    protected BrowseHistoryEntity(Parcel in) {
        taskNameList = in.createStringArrayList();
        integralNum = in.readInt();
        taskProblemList = in.createTypedArrayList(TaskProblemListDTO.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(taskNameList);
        dest.writeInt(integralNum);
        dest.writeTypedList(taskProblemList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BrowseHistoryEntity> CREATOR = new Creator<BrowseHistoryEntity>() {
        @Override
        public BrowseHistoryEntity createFromParcel(Parcel in) {
            return new BrowseHistoryEntity(in);
        }

        @Override
        public BrowseHistoryEntity[] newArray(int size) {
            return new BrowseHistoryEntity[size];
        }
    };

    public List<String> getTaskNameList() {
        return taskNameList;
    }

    public void setTaskNameList(List<String> taskNameList) {
        this.taskNameList = taskNameList;
    }

    public int getIntegralNum() {
        return integralNum;
    }

    public void setIntegralNum(int integralNum) {
        this.integralNum = integralNum;
    }

    public List<TaskProblemListDTO> getProblemList() {
        return taskProblemList;
    }

    public void setProblemList(List<TaskProblemListDTO> problemList) {
        this.taskProblemList = problemList;
    }

    public static class TaskProblemListDTO implements Parcelable {
        private String taskId;
        private String taskName;
        public List<ListDTO> problemList;

        protected TaskProblemListDTO(Parcel in) {
            taskId = in.readString();
            taskName = in.readString();
            problemList = in.createTypedArrayList(ListDTO.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(taskId);
            dest.writeString(taskName);
            dest.writeTypedList(problemList);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<TaskProblemListDTO> CREATOR = new Creator<TaskProblemListDTO>() {
            @Override
            public TaskProblemListDTO createFromParcel(Parcel in) {
                return new TaskProblemListDTO(in);
            }

            @Override
            public TaskProblemListDTO[] newArray(int size) {
                return new TaskProblemListDTO[size];
            }
        };

        public String getTaskId() {
            return taskId;
        }

        public void setTaskId(String taskId) {
            this.taskId = taskId;
        }

        public String getTaskName() {
            return taskName;
        }

        public void setTaskName(String taskName) {
            this.taskName = taskName;
        }

        public List<ListDTO> getProblemList() {
            return problemList;
        }

        public void setProblemList(List<ListDTO> problemList) {
            this.problemList = problemList;
        }

        public static class ListDTO implements Parcelable {
            private String id;
            private String name;
            private String content;
            private String serverCode;
            private String businessId;
            private String businessPage;
            private String businessInfo;
            private String shelvesStatus;
            private int checkAnswer;
            private List<OptionListDTO> optionList;

            protected ListDTO(Parcel in) {
                id = in.readString();
                name = in.readString();
                content = in.readString();
                serverCode = in.readString();
                businessId = in.readString();
                businessPage = in.readString();
                businessInfo = in.readString();
                shelvesStatus = in.readString();
                checkAnswer = in.readInt();
                optionList = in.createTypedArrayList(OptionListDTO.CREATOR);
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(id);
                dest.writeString(name);
                dest.writeString(content);
                dest.writeString(serverCode);
                dest.writeString(businessId);
                dest.writeString(businessPage);
                dest.writeString(businessInfo);
                dest.writeString(shelvesStatus);
                dest.writeInt(checkAnswer);
                dest.writeTypedList(optionList);
            }

            public int getCheckAnswer() {
                return checkAnswer;
            }

            public void setCheckAnswer(int checkAnswer) {
                this.checkAnswer = checkAnswer;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            public static final Creator<ListDTO> CREATOR = new Creator<ListDTO>() {
                @Override
                public ListDTO createFromParcel(Parcel in) {
                    return new ListDTO(in);
                }

                @Override
                public ListDTO[] newArray(int size) {
                    return new ListDTO[size];
                }
            };

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getServerCode() {
                return serverCode;
            }

            public void setServerCode(String serverCode) {
                this.serverCode = serverCode;
            }

            public String getBusinessId() {
                return businessId;
            }

            public void setBusinessId(String businessId) {
                this.businessId = businessId;
            }

            public String getBusinessPage() {
                return businessPage;
            }

            public void setBusinessPage(String businessPage) {
                this.businessPage = businessPage;
            }

            public String getBusinessInfo() {
                return businessInfo;
            }

            public void setBusinessInfo(String businessInfo) {
                this.businessInfo = businessInfo;
            }

            public String getShelvesStatus() {
                return shelvesStatus;
            }

            public void setShelvesStatus(String shelvesStatus) {
                this.shelvesStatus = shelvesStatus;
            }

            public List<OptionListDTO> getOptionList() {
                return optionList;
            }

            public void setOptionList(List<OptionListDTO> optionList) {
                this.optionList = optionList;
            }

            public static class OptionListDTO implements Parcelable {
                private int id;
                private String problemId;
                private String optionContent;//内容
                private String optionPicture;//icon
                private int correctOption;//1 正确 2 错误
                private boolean isSelectStatus;//答题选中状态

                public boolean isSelectStatus() {
                    return isSelectStatus;
                }

                public void setSelectStatus(boolean selectStatus) {
                    isSelectStatus = selectStatus;
                }

                @RequiresApi(api = Build.VERSION_CODES.Q)
                protected OptionListDTO(Parcel in) {
                    id = in.readInt();
                    problemId = in.readString();
                    optionContent = in.readString();
                    optionPicture = in.readString();
                    correctOption = in.readInt();
                    isSelectStatus= in.readBoolean();
                }

                public static final Creator<OptionListDTO> CREATOR = new Creator<OptionListDTO>() {
                    @SuppressLint("NewApi")
                    @Override
                    public OptionListDTO createFromParcel(Parcel in) {
                        return new OptionListDTO(in);
                    }

                    @Override
                    public OptionListDTO[] newArray(int size) {
                        return new OptionListDTO[size];
                    }
                };

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getProblemId() {
                    return problemId;
                }

                public void setProblemId(String problemId) {
                    this.problemId = problemId;
                }

                public String getOptionContent() {
                    return optionContent;
                }

                public void setOptionContent(String optionContent) {
                    this.optionContent = optionContent;
                }

                public String getOptionPicture() {
                    return optionPicture;
                }

                public void setOptionPicture(String optionPicture) {
                    this.optionPicture = optionPicture;
                }

                public int getCorrectOption() {
                    return correctOption;
                }

                public void setCorrectOption(int correctOption) {
                    this.correctOption = correctOption;
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @SuppressLint("NewApi")
                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(id);
                    dest.writeString(problemId);
                    dest.writeString(optionContent);
                    dest.writeString(optionPicture);
                    dest.writeInt(correctOption);
                    dest.writeBoolean(isSelectStatus);
                }
            }
        }
    }
}
