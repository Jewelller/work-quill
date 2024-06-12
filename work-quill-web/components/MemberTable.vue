<template>
    <div>
        <v-data-table :headers="headers" :items="data.records">
            <template v-slot:top>
                <v-toolbar flat>
                    <v-toolbar-title>{{ toolbarTitle }}</v-toolbar-title>
                    <v-dialog v-model="dialog" max-width="500px">
                        <template v-slot:activator="{ props }">
                            <v-btn color="primary" dark v-bind="props">
                                {{ $t("modifiable_table.new_item") }}
                            </v-btn>
                        </template>
                        <v-card>
                            <v-card-title>
                                <span class="text-h5">{{ formTitle }}</span>
                            </v-card-title>

                            <v-card-text>
                                <v-container>
                                    <v-row>
                                        <v-col cols="12" md="4" sm="4">
                                            <v-text-field
                                                disabled
                                                v-model="editedItem.memberId"
                                                label="Member ID"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12" md="8" sm="8">
                                            <v-text-field
                                                v-model="editedItem.name"
                                                label="Name"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field
                                                v-model="
                                                    editedItem.departmentId
                                                "
                                                label="DepartmentID"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12" md="6" sm="6">
                                            <v-text-field
                                                v-model="editedItem.gender"
                                                label="Gender"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12" md="6" sm="6">
                                            <v-text-field
                                                v-model="editedItem.birthday"
                                                label="Birthday"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field
                                                v-model="
                                                    editedItem.eduBackground
                                                "
                                                label="Edu. Background"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field
                                                v-model="editedItem.phone"
                                                label="Phone"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field
                                                v-model="editedItem.email"
                                                label="Email"
                                            ></v-text-field>
                                        </v-col>
                                        <v-col cols="12">
                                            <v-text-field
                                                v-model="
                                                    editedItem.memberAddress
                                                "
                                                label="Address"
                                            ></v-text-field>
                                        </v-col>
                                    </v-row>
                                </v-container>
                            </v-card-text>

                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn
                                    color="blue-darken-1"
                                    variant="text"
                                    @click="close"
                                >
                                    Cancel
                                </v-btn>
                                <v-btn
                                    color="blue-darken-1"
                                    variant="text"
                                    @click="save"
                                >
                                    Save
                                </v-btn>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                    <v-dialog v-model="dialogDelete" max-width="500px">
                        <v-card>
                            <v-card-title class="text-h5"
                                >Are you sure you want to delete this
                                item?</v-card-title
                            >
                            <v-card-actions>
                                <v-spacer></v-spacer>
                                <v-btn
                                    color="blue-darken-1"
                                    variant="text"
                                    @click="closeDelete"
                                    >Cancel</v-btn
                                >
                                <v-btn
                                    color="blue-darken-1"
                                    variant="text"
                                    @click="deleteItemConfirm"
                                    >OK</v-btn
                                >
                                <v-spacer></v-spacer>
                            </v-card-actions>
                        </v-card>
                    </v-dialog>
                </v-toolbar>
            </template>
            <template v-slot:item.actions="{ item }">
                <v-icon class="me-2" size="small" @click="editItem(item)">
                    mdi-pencil
                </v-icon>
                <v-icon size="small" @click="deleteItem(item)">
                    mdi-delete
                </v-icon>
            </template>
            <template v-slot:no-data>{{
                $t("modifiable_table.no_data_available")
            }}</template>
        </v-data-table>
    </div>
</template>

<script>
import { BaseService } from "~/src/services/BaseService";

export default {
    props: {
        headers: Array,
        toolbarTitle: String,
    },
    data() {
        return {
            userService: new BaseService(API_URL.member),
            data: {},
            loading: false,
            dialog: false,
            dialogDelete: false,
            editedIndex: -1,
            editedItem: {
                memberId: null,
                name: null,
                departmentId: null,
                gender: null,
                birthday: null,
                eduBackground: null,
                phone: null,
                email: null,
                memberAddress: null,
            },
            defaultItem: {
                memberId: null,
                name: null,
                departmentId: null,
                gender: null,
                birthday: null,
                eduBackground: null,
                phone: null,
                email: null,
                memberAddress: null,
            },
        };
    },
    computed: {
        formTitle() {
            return this.editedIndex === -1 ? "New Item" : "Edit Item";
        },
    },
    mounted() {
        this.getAll();
    },
    methods: {
        getAll() {
            this.userService
                .getAll()
                .then((res) => {
                    log.debug(res);
                    this.data = res.data.data;
                })
                .catch((err) => {
                    log.error(err);
                });
        },
        editItem(item) {
            log.debug(item);
            this.editedIndex = this.data.records.findIndex(
                (v) => v.userId == item.userId
            );
            this.editedItem = Object.assign({}, item);
            log.debug("editItem: ", item, this.editedIndex, this.editedItem);
            this.dialog = true;
        },
        deleteItem(item) {
            log.debug(item);
            this.editedIndex = this.data.records.findIndex(
                (v) => v.userId == item.userId
            );
            this.editedItem = Object.assign({}, item);
            this.dialogDelete = true;
        },
        deleteItemConfirm() {
            log.debug(this.data.records);
            this.userService
                .delete([this.editedItem.userId])
                .then((res) => {
                    log.debug("Delete success: ", res);
                    Notify.success("Delete success!");
                    this.data.records.splice(this.editedIndex, 1);
                })
                .catch((err) => {
                    Notify.failure("Delete failed!");
                    log.error(err);
                });

            this.closeDelete();
        },
        close() {
            this.dialog = false;
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem);
                this.editedIndex = -1;
            });
        },
        closeDelete() {
            this.dialogDelete = false;
            this.$nextTick(() => {
                this.editedItem = Object.assign({}, this.defaultItem);
                this.editedIndex = -1;
            });
        },
        save() {
            if (this.editedIndex > -1) {
                // 修改
                log.debug("Saving Changes: ", this.editedItem);

                this.userService
                    .update(this.editedItem)
                    .then((res) => {
                        log.info("Update: ", res);
                        Notify.success("Changes saved");
                    })
                    .catch((err) => {
                        log.error(err);
                        Notify.failure("Changes are not saved!");
                    });

                this.data.records[this.editedIndex] = this.editedItem;
            } else {
                // 新增
                this.userService
                    .add(this.editedItem)
                    .then((res) => {
                        log.success("New item added: ", res);
                        Notify.success("New item added");

                        this.getAll();
                    })
                    .catch((err) => {
                        log.error(err);
                        Notify.failure("Add item failed!");
                    });
            }
            this.close();
        },
    },
};
</script>

<style></style>
